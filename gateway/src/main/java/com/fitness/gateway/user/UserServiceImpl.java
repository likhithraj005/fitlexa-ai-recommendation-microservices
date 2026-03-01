package com.fitness.gateway.user;

import com.fitness.gateway.exception.InvalidUserException;
import com.fitness.gateway.exception.ResourceNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final WebClient userServiceWebClient;

    @Override
    @CircuitBreaker(name = "userServiceGatewayCB", fallbackMethod = "fallbackUserSync")
    public Mono<Boolean> validateUser(String userId) {

        log.info("Calling User Validation API for userId: {}", userId);

        return userServiceWebClient.get()
                .uri("/api/users/{userId}/validate", userId)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        Mono.error(new ResourceNotFoundException(
                                "User not found with id: " + userId)))
                .onStatus(HttpStatus.BAD_REQUEST::equals, response ->
                        Mono.error(new InvalidUserException(
                                "Invalid User Request: " + userId)))
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, response ->
                        Mono.error(new RuntimeException(
                                "User Service is unavailable")))
                .bodyToMono(Boolean.class);
    }

    @Override
    public Mono<UserResponse> registerUser(RegisterRequest request) {

        log.info("Calling User Registration API for email: {}", request.getEmail());

        return userServiceWebClient.post()
                .uri("/api/users/register")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status == HttpStatus.BAD_REQUEST,
                        response -> Mono.error(
                                new InvalidUserException("Invalid registration request")
                        )
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> Mono.error(
                                new RuntimeException("User Service is unavailable")
                        )
                )
                .bodyToMono(UserResponse.class);
    }

    public Mono<Boolean> fallbackUserSync(String userId, Throwable ex) {

        log.error("Gateway fallback triggered for userId: {}", userId);

        // You decide behavior:
        // Option 1: Allow request to continue
        return Mono.just(false);

        // Option 2: Return error
        // return Mono.error(new RuntimeException("User service unavailable"));
    }

}
