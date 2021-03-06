package com.eric.monitoringserverjava.users;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 */
@RequestMapping("api/users/")
@RestController
public class UserController {
    private UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    Flux<User> getUsers () {
        return userService.getUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    Mono<User> getUserById (@PathVariable Publisher<String> id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    Mono<ResponseEntity<User>> createUser (@RequestBody User user) {
        return userService.createUser(user)
                          .map(
                                  createdUser -> new ResponseEntity<>(createdUser, HttpStatus.CREATED)
                          )
                          .defaultIfEmpty(ResponseEntity.noContent()
                                                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.PUT)
    Mono<ResponseEntity<User>> updateUser (@RequestBody User user) {
        return userService.updateUser(user)
                          .map(
                                  createdUser -> new ResponseEntity<>(createdUser, HttpStatus.OK)
                          )
                          .defaultIfEmpty(ResponseEntity.noContent()
                                                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE)
    Mono<Void> deleteUser (@RequestBody User user) {
        return userService.deleteUser(user);
    }
}
