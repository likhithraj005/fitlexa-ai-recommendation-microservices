package com.fitness.userservice.services;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.exception.ResourceNotFoundException;
import com.fitness.userservice.exception.UserAlreadyExistsException;
import com.fitness.userservice.models.User;
import com.fitness.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            //throw new UserAlreadyExistsException("Email already exist");

            User existingUser = userRepository.findByEmail(request.getEmail());

            UserResponse response = new UserResponse();
            response.setId(existingUser.getId());
            response.setKeycloakId(existingUser.getKeycloakId());
            response.setEmail(existingUser.getEmail());
            response.setPassword(existingUser.getPassword());
            response.setFirstName(existingUser.getFirstName());
            response.setLastName(existingUser.getLastName());
            response.setCreatedAt(existingUser.getCreatedAt());
            response.setUpdatedAt(existingUser.getUpdatedAt());

            return response;
        }

        User user = mapToEntity(request);

        System.out.println("Before save count = " + userRepository.count());
        User saveUser = userRepository.save(user);
        System.out.println("After save count = " + userRepository.count());

        return mapToResponse(saveUser);

    }

    @Override
    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToResponse(user);
    }

    @Override
    public Boolean existUserById(String userId) {
        log.info("Calling User Validation API for userId: {}", userId);
//        return userRepository.existsById(userId);
        return userRepository.existsByKeycloakId(userId);
    }

//    @Override
//    public Boolean existOrCreateUser(Jwt jwt) {
//
//        String keycloakId = jwt.getSubject();
//
//        User existingUser = userRepository.findByKeycloakId(keycloakId);
//
//        if(existingUser != null){
//            return true;
//        }
//
//        User user = new User();
//        user.setKeycloakId(keycloakId);
//        user.setEmail(jwt.getClaim("email"));
//        user.setFirstName(jwt.getClaim("given_name"));
//        user.setLastName(jwt.getClaim("family_name"));
//        user.setPassword("dummy");
//
//        userRepository.save(user);
//
//        return true;
//    }



    public User mapToEntity(RegisterRequest request){
        User user = new User();
        user.setKeycloakId(request.getKeycloakId());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return user;
    }

    public UserResponse mapToResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setKeycloakId(user.getKeycloakId());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}
