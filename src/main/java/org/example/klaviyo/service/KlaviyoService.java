package org.example.klaviyo.service;

import org.example.klaviyo.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KlaviyoService {

    private final WebClient klaviyoWebClient;

    // Profile Operations
    public Mono<KlaviyoResponse<KlaviyoProfile>> createProfile(KlaviyoProfile profile) {
        KlaviyoRequest<KlaviyoProfile> request = new KlaviyoRequest<>(profile);

        return klaviyoWebClient.post()
                .uri("/profiles")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<KlaviyoResponse<KlaviyoProfile>>() {})
                .doOnSuccess(r -> log.info("Profile created successfully"))
                .doOnError(e -> log.error("Error creating profile: {}", e.getMessage()));
    }

    public Mono<KlaviyoResponse<KlaviyoProfile>> getProfile(String profileId) {
        return klaviyoWebClient.get()
                .uri("/profiles/{id}", profileId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<KlaviyoResponse<KlaviyoProfile>>() {})
                .doOnError(e -> log.error("Error fetching profile: {}", e.getMessage()));
    }

    public Mono<KlaviyoResponse<KlaviyoProfile>> updateProfile(String profileId, KlaviyoProfile profile) {
        profile.setId(profileId);
        KlaviyoRequest<KlaviyoProfile> request = new KlaviyoRequest<>(profile);

        return klaviyoWebClient.patch()
                .uri("/profiles/{id}", profileId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<KlaviyoResponse<KlaviyoProfile>>() {})
                .doOnError(e -> log.error("Error updating profile: {}", e.getMessage()));
    }

    public Mono<Void> deleteProfile(String profileId) {
        return klaviyoWebClient.delete()
                .uri("/profiles/{id}", profileId)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(r -> log.info("Profile deleted successfully"))
                .doOnError(e -> log.error("Error deleting profile: {}", e.getMessage()));
    }

    // Event Operations
    public Mono<Void> trackEvent(KlaviyoEvent event) {
        KlaviyoRequest<KlaviyoEvent> request = new KlaviyoRequest<>(event);

        return klaviyoWebClient.post()
                .uri("/events")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(r -> log.info("Event tracked successfully"))
                .doOnError(e -> log.error("Error tracking event: {}", e.getMessage()));
    }

    // List Operations - keeping as Map since structure varies
    public Mono<Map> getLists(Integer pageSize) {
        return klaviyoWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lists")
                        .queryParam("page[size]", pageSize != null ? pageSize : 20)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .doOnError(e -> log.error("Error fetching lists: {}", e.getMessage()));
    }

    public Mono<Map> addProfileToList(String listId, String profileId) {
        Map<String, Object> request = Map.of(
                "data", new Object[]{
                        Map.of(
                                "type", "profile",
                                "id", profileId
                        )
                }
        );

        return klaviyoWebClient.post()
                .uri("/lists/{id}/relationships/profiles", listId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .doOnError(e -> log.error("Error adding profile to list: {}", e.getMessage()));
    }

    // Metrics Operations
    public Mono<Map> getMetrics() {
        return klaviyoWebClient.get()
                .uri("/metrics")
                .retrieve()
                .bodyToMono(Map.class)
                .doOnError(e -> log.error("Error fetching metrics: {}", e.getMessage()));
    }
}