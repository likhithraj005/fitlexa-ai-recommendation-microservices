package com.fitness.activityservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ActivityType {
    RUNNING,
    WALKING,
    CYCLING,
    SWIMMING,
    WEIGHT_TRAINING,
    YOGA,
    HIIT, //High-Intensity Interval Training
    CARDIO,
    STRETCHING,
    OTHER;

    @JsonCreator
    public static ActivityType from(String value) {
        return ActivityType.valueOf(value.toUpperCase());
    }
}
