package com.fitness.activityservice.service;

import com.fitness.activityservice.exception.InvalidUserException;
import com.fitness.activityservice.exception.ResourceNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationServiceImpl implements UserValidationService{

    private final WebClient userServiceWebClient;

//    @Override
//    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackValidateUser")
//    public boolean validateUser(String userId){

//    @Override
//    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackValidateUser")
//    public boolean validateUser(String token){
//
////        log.info("Calling User Validation API for userId: {}", userId);
//        log.info("Calling User Validation API");
//
//        return userServiceWebClient.get()
//                //.uri("/api/users/{userId}/validate", userId)
//                .uri("/api/users/validate")
//                .header("Authorization", "Bearer " + token)
//                .retrieve()
//                .onStatus(
//                        status -> status.is5xxServerError(),
//                        response -> response.createException()
//                )
//                .bodyToMono(Boolean.class)
////                .onErrorResume(ex -> {
////                    log.error("Error while calling USER-SERVICE", ex);
////                    return Mono.error(new RuntimeException("User Service Down"));
////                })
//                .block();
//
////        try {
////            return userServiceWebClient.get()
////                    .uri("/api/users/{userId}/validate", userId)
////                    .retrieve()
////                    .bodyToMono(Boolean.class)
////                    .block();
////        }catch (WebClientResponseException ex){
////            if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
////                throw new ResourceNotFoundException("User not found with id: " + userId);
////            } else if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
////                throw new InvalidUserException("Invalid User Request: " + userId);
////            } else if (ex.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
////                throw new RuntimeException("User Service is unavailable: " + ex.getMessage());
////            }
////        }
////        return false;
//    }


    @Override
    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackValidateUser")
    public boolean validateUser(String userId) {
        log.info("Validating user via USER-SERVICE for userId: {}", userId);

        try {
            return userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                log.error("Invalid user request for userId {}: {}", userId, ex.getMessage());
                return false;
            } else {
                throw ex;
            }
        }
    }

    // Fallback triggered if USER-SERVICE is down
    public boolean fallbackValidateUser(String userId, Throwable ex) {
        log.error("USER-SERVICE down. Fallback triggered for userId: {}", userId, ex);
        return false; // Graceful degradation
    }

//    public boolean fallbackValidateUser(String userId, Throwable ex) {
//        log.error("User Service is down. Fallback triggered for userId: {}", userId);
//        return false;
//    }

//    public boolean fallbackValidateUser(String token, Throwable ex) {
//        log.error("User Service is down. Fallback triggered", ex);
//        return false;
//    }

}
