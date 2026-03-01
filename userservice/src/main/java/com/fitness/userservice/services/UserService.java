package com.fitness.userservice.services;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import org.jspecify.annotations.Nullable;



public interface UserService {
    UserResponse register(RegisterRequest request);

    UserResponse getUserProfile(String userId);

    Boolean existUserById(String userId);

//    Boolean existOrCreateUser(Jwt jwt);

}
