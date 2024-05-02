package org.bank.ssalguerof.msvc.yanki.customers.services;

import org.bank.ssalguerof.msvc.yanki.customers.documents.Transaccion;
import org.bank.ssalguerof.msvc.yanki.customers.documents.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    public Flux<User> findAll();
    public Mono<User> findbyId(String id);
    public Mono<User> save(User user);
    public Mono<Void> delete(User user);
    public Mono<User> addTransaction(String idUser, Transaccion transaccion);

}
