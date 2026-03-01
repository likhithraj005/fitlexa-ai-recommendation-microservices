package com.fitness.aiservice.controller;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.service.RecommendationService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final MongoTemplate mongoTemplate;

    private final RecommendationService recommendationService;

    @PostConstruct
    public void printMongoInfo() {
        System.out.println("Mongo DB name = " + mongoTemplate.getDb().getName());
        System.out.println("Mongo URI = " + mongoTemplate.getMongoDatabaseFactory());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.getUserRecommendation(userId));
    }

//    @GetMapping("/activity/{activityId}")
//    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId){
//        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
//    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<?> getActivityRecommendation(@PathVariable String activityId){

        Recommendation recommendation =
                recommendationService.getActivityRecommendation(activityId);

        if (recommendation == null) {
            //return ResponseEntity.ok(null);
            return ResponseEntity.status(404)
                    .body("Recommendation not ready yet");
        }

        return ResponseEntity.ok(recommendation);
    }
}
