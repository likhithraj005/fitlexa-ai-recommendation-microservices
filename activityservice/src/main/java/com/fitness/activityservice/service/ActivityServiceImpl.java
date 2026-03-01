package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.exception.InvalidUserException;
import com.fitness.activityservice.exception.ResourceNotFoundException;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.model.ActivityType;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.jspecify.annotations.Nullable;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService{

//    @Autowired
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {

        boolean isValidUser = userValidationService.validateUser(request.getUserId());
        if(!isValidUser){
            throw new InvalidUserException("Invalid User Request: " + request.getUserId());
        }

        ActivityType activityType;
        try {
            activityType = ActivityType.valueOf(
                    request.getActivityType().toUpperCase()
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Activity Type: " + request.getActivityType());
        }

        LocalDateTime startTime = request.getStartTime() != null
                ? request.getStartTime()
                : LocalDateTime.now();

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .activityType(activityType)
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(startTime)
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        System.out.println("Before save count = " + activityRepository.count());
        Activity savedActivity = activityRepository.save(activity);
        System.out.println("After save count = " + activityRepository.count());

        // Publish to RabbitMQ for AI Processing
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        }catch (Exception e){
            log.error("Failed to publish activity to RabbitMQ : ", e);
        }


        return mapToResponse(savedActivity);
    }

//    @Override
//    public ActivityResponse trackActivity(ActivityRequest request, String authHeader) {
//    @Override
//    public ActivityResponse trackActivity(ActivityRequest request) {
//
//        //String token = authHeader.replace("Bearer ", "");
//
//        //boolean isValidUser = userValidationService.validateUser(token);
//
////        if (!isValidUser) {
////            throw new InvalidUserException("Invalid or Unavailable User Service");
////        }
//
//        if(request.getUserId() == null || !userValidationService.validateUser(request.getUserId())) {
//            throw new InvalidUserException("Invalid or unavailable user");
//        }
//
//        Activity activity = Activity.builder()
//                .userId(request.getUserId())
//                .activityType(request.getActivityType())
//                .duration(request.getDuration())
//                .caloriesBurned(request.getCaloriesBurned())
//                .startTime(request.getStartTime())
//                .additionalMetrics(request.getAdditionalMetrics())
//                .build();
//
//        Activity savedActivity = activityRepository.save(activity);
//
//        try {
//            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
//        } catch (Exception e) {
//            log.error("Failed to publish activity to RabbitMQ : ", e);
//        }
//
//        return mapToResponse(savedActivity);
//    }

    @Override
    public List<ActivityResponse> getUserActivities(String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        boolean isValidUser = userValidationService.validateUser(token);

        if (!isValidUser) {
            throw new InvalidUserException("Invalid or Unavailable User Service");
        }

        List<Activity> activities = activityRepository.findAll();

        return activities.stream()
                .map(activity -> {
                    ActivityResponse response = new ActivityResponse();
                    response.setId(activity.getId());
                    response.setUserId(activity.getUserId());
                    response.setActivityType(activity.getActivityType());
                    response.setDuration(activity.getDuration());
                    response.setCaloriesBurned(activity.getCaloriesBurned());
                    response.setStartTime(activity.getStartTime());
                    response.setAdditionalMetrics(activity.getAdditionalMetrics());
                    response.setCreatedAt(activity.getCreatedAt());
                    response.setUpdatedAt(activity.getUpdatedAt());
                    return response;
                })
                .toList();
    }



    @Override
    public List<ActivityResponse> getUserActivitiesByUserId(String userId) {
;
//        List<Activity> activities = activityRepository.findAll();
        List<Activity> activities = activityRepository.findByUserId(userId);

//        if(activities.isEmpty()){
//            throw new ResourceNotFoundException("userId not found with id: " + userId);
//        }

        return activities.stream().map(activity -> {
            ActivityResponse response = new ActivityResponse();
            response.setId(activity.getId());
            response.setUserId(activity.getUserId());
            response.setActivityType(activity.getActivityType());
            response.setDuration(activity.getDuration());
            response.setCaloriesBurned(activity.getCaloriesBurned());
            response.setStartTime(activity.getStartTime());
            response.setAdditionalMetrics(activity.getAdditionalMetrics());
            response.setCreatedAt(activity.getCreatedAt());
            response.setUpdatedAt(activity.getUpdatedAt());

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ActivityResponse> getUserActivities() {

        List<Activity> activities = activityRepository.findAll();

        return activities.stream().map(activity -> {
            ActivityResponse response = new ActivityResponse();
            response.setId(activity.getId());
            response.setUserId(activity.getUserId());
            response.setActivityType(activity.getActivityType());
            response.setDuration(activity.getDuration());
            response.setCaloriesBurned(activity.getCaloriesBurned());
            response.setStartTime(activity.getStartTime());
            response.setAdditionalMetrics(activity.getAdditionalMetrics());
            response.setCreatedAt(activity.getCreatedAt());
            response.setUpdatedAt(activity.getUpdatedAt());

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public ActivityResponse getActivityById(String activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("activityId not found with id: " + activityId));
        return mapToResponse(activity);
    }

    @Override
    public void deleteActivity(String id) {
        if (!activityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Activity not found: " + id);
        }
        activityRepository.deleteById(id);
    }


    public ActivityResponse mapToResponse(Activity activity){
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setActivityType(activity.getActivityType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());

        return response;
    }
}
