package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
//import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ActivityService {
    ActivityResponse trackActivity(ActivityRequest request);

//    ActivityResponse trackActivity(ActivityRequest request, String authHeader);

    List<ActivityResponse> getUserActivities(String authHeader);


    List<ActivityResponse> getUserActivitiesByUserId(String userId);

    List<ActivityResponse> getUserActivities();

    ActivityResponse getActivityById(String activityId);

    void deleteActivity(String id);
}
