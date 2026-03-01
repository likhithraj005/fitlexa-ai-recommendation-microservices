package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

//    @Autowired
    private final ActivityService activityService;

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void printMongoInfo() {
        System.out.println("Mongo DB name = " + mongoTemplate.getDb().getName());
        System.out.println("Mongo URI = " + mongoTemplate.getMongoDatabaseFactory());
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request, @RequestHeader("X-User-ID") String userId){

        if(userId != null){
            request.setUserId(userId);
        }

        return ResponseEntity.ok(activityService.trackActivity(request));
    }

//    @PostMapping
//    public ResponseEntity<ActivityResponse> trackActivity(
//            @RequestBody ActivityRequest request,
//            @RequestHeader("Authorization") String authHeader) {
//
//        return ResponseEntity.ok(
//                activityService.trackActivity(request, authHeader)
//        );
//    }

//    @PostMapping
//    public ResponseEntity<ActivityResponse> trackActivity(
//            @RequestBody ActivityRequest request,
//            @AuthenticationPrincipal Jwt jwt) {
//
//        // Extract userId from JWT
//        String userId = jwt.getSubject(); // Keycloak 'sub'
//        request.setUserId(userId);
//
//        return ResponseEntity.ok(
//                activityService.trackActivity(request)
//        );
//    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getActivities(
            @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(activityService.getUserActivitiesByUserId(userId));
    }

//    @GetMapping
//    public ResponseEntity<List<ActivityResponse>> getActivities(@AuthenticationPrincipal Jwt jwt) {
//        String userId = jwt.getSubject();
//        return ResponseEntity.ok(activityService.getUserActivitiesByUserId(userId));
//    }



    @GetMapping("/all")
    public ResponseEntity<List<ActivityResponse>> getUserActivities(){
        return ResponseEntity.ok(activityService.getUserActivities());
    }

    @GetMapping("/user")
    public ResponseEntity<List<ActivityResponse>> getUserActivitiesByUserId(@RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(activityService.getUserActivitiesByUserId(userId));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable String activityId){
        return ResponseEntity.ok(activityService.getActivityById(activityId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable String id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
