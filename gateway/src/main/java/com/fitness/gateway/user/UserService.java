package com.fitness.gateway.user;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Boolean> validateUser(String userId);

    Mono<UserResponse> registerUser(RegisterRequest registerRequest);
}
