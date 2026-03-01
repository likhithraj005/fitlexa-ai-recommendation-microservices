//package com.fitness.aiservice.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.Map;
//
//@Service
//public class GeminiService {
//
//    private final WebClient webClient;
//
//    @Value("${gemini.api.url}")
//    private String geminiApiUrl;
//
//    @Value("${gemini.api.key}")
//    private String geminiApiKey;
//
//    public GeminiService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.build();
//    }
//
//    public String getAnswer(String question){
//        Map<String, Object> requestBody = Map.of(
//                "contents", new Object[] {
//                        Map.of(
//                                "parts", new Object[] {
//                                        Map.of("text", question)
//                                }
//                        )
//                }
//        );
//
//        String response = webClient.post()
//                .uri(geminiApiUrl + geminiApiKey)
//                .header("Content-Type", "application/json")
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        return response;
//    }
//}

package com.fitness.aiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public String getAnswer(String question) {

        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text", question)
                                }
                        )
                }
        );

//        return webClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .path(geminiApiUrl)
//                        .queryParam("key", geminiApiKey)
//                        .build())
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(String.class)
//                .onErrorReturn("{\"error\":\"Gemini failed\"}")
//                .block();

//        return webClient.post()
////                .uri(geminiApiUrl + "?key=" + geminiApiKey)
//                .uri(geminiApiUrl + geminiApiKey)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();

        String fullUrl = geminiApiUrl + "?key=" + geminiApiKey;

        System.out.println("Calling Gemini URL: " + fullUrl);

        return webClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .path(geminiApiUrl)
//                        .queryParam("key", geminiApiKey)
//                        .build())
                .uri(fullUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("{\"error\":\"Gemini failed\"}")
                .block();
    }
}