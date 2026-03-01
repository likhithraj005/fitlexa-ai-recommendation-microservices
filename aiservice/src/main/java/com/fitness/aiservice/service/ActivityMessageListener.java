package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(
            queues = "activity.queue",
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void processActivity(Activity activity) {
        try {
            log.info("Received activity for processing: {}", activity.getId());

            Recommendation recommendation = activityAIService.generateRecommendation(activity);

            log.info("Generated Recommendation: {}", recommendation);

            recommendationRepository.save(recommendation);
        } catch (Exception e) {
            log.error("Failed to process activity: {}", activity, e);
            throw e;
        }
    }
}

//A Dead Letter Queue is a special queue where messages go when they cannot be processed successfully.
//Instead of:
// retrying forever
// freezing your service
// spamming logs
// burning CPU & API credits
//️ the message is moved aside safely for inspection.

//Without DLQ (what you already experienced)
//Activity message
//   ↓
//AI Service throws exception
//   ↓
//RabbitMQ requeues message
//   ↓
//Listener picks it again
//   ↓
//Exception again
//   ↓
//♾ infinite loop
//   ↓
//IntelliJ lag / laptop fan / madness

//With DLQ (civilized system)
//Activity message
//   ↓
//AI Service throws exception
//   ↓
//Message rejected
//   ↓
//RabbitMQ moves it to DLQ
//   ↓
//Main queue continues normally
