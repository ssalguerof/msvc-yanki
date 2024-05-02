package org.bank.ssalguerof.msvc.yanki.customers.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bank.ssalguerof.msvc.yanki.customers.dao.UserDao;
import org.bank.ssalguerof.msvc.yanki.customers.documents.Transaccion;
import org.bank.ssalguerof.msvc.yanki.customers.documents.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public Flux<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Mono<User> findbyId(String id) {
        return userDao.findById(id);
    }

    @Override
    public Mono<User> save(User user) {
        return userDao.save(user);
    }

    @Override
    public Mono<Void> delete(User user) {
        return userDao.delete(user);
    }

    @Override
    public Mono<User> addTransaction(String idUser, Transaccion transaccion) {
        return userDao.findById(idUser)
                .flatMap(user -> {

                    //if(!Optional.ofNullable(user.getTransaccionList()).isPresent()){
                        user.setTransaccionList(new ArrayList<>());
                    //};
                    user.getTransaccionList().add(transaccion);

                    String dataTopico = entityToJson(user);

                    ListenableFuture<SendResult<String, String>> future =
                            kafkaTemplate.send("yanki-topic", dataTopico);
                    future.addCallback(new KafkaSendCallback<String, String>(){

                        @Override
                        public void onSuccess(SendResult<String, String> result) {
                            log.info("Message sent ", result.getRecordMetadata().offset());
                        }

                        @Override
                        public void onFailure(Throwable ex) {
                            log.error("Error sending message ", ex);
                        }

                        @Override
                        public void onFailure(KafkaProducerException e) {
                            log.error("Error sending message ", e);
                        }
                    });

                    return Mono.just(user);
                });
    }

    private String entityToJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        String strJSON = null;
        try {
            strJSON = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            
            log.error("Error al convertir: "+e.getMessage());
        }
        return strJSON;

    }




}
