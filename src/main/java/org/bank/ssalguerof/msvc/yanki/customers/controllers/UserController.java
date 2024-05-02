package org.bank.ssalguerof.msvc.yanki.customers.controllers;

import org.bank.ssalguerof.msvc.yanki.customers.documents.Transaccion;
import org.bank.ssalguerof.msvc.yanki.customers.documents.User;
import org.bank.ssalguerof.msvc.yanki.customers.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable String id) {
        return userService.findbyId(id);
    }

    @PostMapping
    public Mono<User> saveProduct(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping
    public Mono<User> updateProduct(@RequestBody User user) {
        return userService.findbyId(user.getId())
                .flatMap(existingProduct -> {
                    BeanUtils.copyProperties(user, existingProduct, "id");
                    return userService.save(existingProduct);
                });
    }

    @PostMapping("/transaction/{idUser}")
    public Mono<User> addTransactionCard(@PathVariable String idUser, @RequestBody Transaccion transaccion){
        return userService.addTransaction(idUser, transaccion);
    }
}
