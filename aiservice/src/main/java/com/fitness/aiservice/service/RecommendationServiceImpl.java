package com.fitness.aiservice.service;

import com.fitness.aiservice.exception.ResourceNotFoundException;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService{

    private final RecommendationRepository recommendationRepository;

    @Override
    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public Recommendation getActivityRecommendation(String activityId) {
        //return recommendationRepository.findByActivityId(activityId).orElseThrow(() -> new ResourceNotFoundException("No Recommendation Found for this activity : " + activityId));
        return recommendationRepository
                .findByActivityId(activityId)
                .orElse(null);
    }
}
