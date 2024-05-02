package org.bank.ssalguerof.msvc.yanki.customers.dao;

import org.bank.ssalguerof.msvc.yanki.customers.documents.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserDao  extends ReactiveMongoRepository<User, String> {
    Mono<User> findByNumCelular(String numCelular);
}
