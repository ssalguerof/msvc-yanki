package org.bank.ssalguerof.msvc.yanki;

import org.bank.ssalguerof.msvc.yanki.customers.dao.RedisDao;
import org.bank.ssalguerof.msvc.yanki.customers.dao.UserDao;
import org.bank.ssalguerof.msvc.yanki.customers.documents.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;


@SpringBootApplication
public class MsvcYankiApplication implements CommandLineRunner {

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Autowired
	private UserDao userDao;

	@Autowired
	private RedisDao redisDao;

	private static final Logger log = LoggerFactory.getLogger(MsvcYankiApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(MsvcYankiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("users").subscribe();
		Flux<User> userFlux = Flux.just(new User(null, "01","Saul", "Salguero", "Florez", "986667744", "GF65757567", "ssalguer@gamil.com",250.20
				,"1", "2344234456783456", null ))
				.flatMap(user -> {
					return userDao.save(user);
				});

		userFlux.subscribe();
		redisDao.save("prueba", "datos de prueba");
	}
}
