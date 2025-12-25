package org.example.klaviyo.controller;

import org.example.klaviyo.model.*;
import org.example.klaviyo.service.KlaviyoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/klaviyo")
@RequiredArgsConstructor
public class KlaviyoController {

    private final KlaviyoService klaviyoService;

    // Profile Endpoints
    @PostMapping("/profiles")
    public Mono<ResponseEntity<KlaviyoResponse<KlaviyoProfile>>> createProfile(
            @RequestBody KlaviyoProfile profile) {
        return klaviyoService.createProfile(profile)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/profiles/{profileId}")
    public Mono<ResponseEntity<KlaviyoResponse<KlaviyoProfile>>> getProfile(
            @PathVariable String profileId) {
        return klaviyoService.getProfile(profileId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/profiles/{profileId}")
    public Mono<ResponseEntity<KlaviyoResponse<KlaviyoProfile>>> updateProfile(
            @PathVariable String profileId,
            @RequestBody KlaviyoProfile profile) {
        return klaviyoService.updateProfile(profileId, profile)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                .defaultIfEmpty(ResponseEntity.badRequest().build());

    }

    @DeleteMapping("/profiles/{profileId}")
    public Mono<ResponseEntity<Void>> deleteProfile(@PathVariable String profileId) {
        return klaviyoService.deleteProfile(profileId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Event Endpoints
    @PostMapping("/events")
    public Mono<ResponseEntity<Void>> trackEvent(@RequestBody KlaviyoEvent event) {
        return klaviyoService.trackEvent(event)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // List Endpoints
    @GetMapping("/lists")
    public Mono<ResponseEntity<Map>> getLists(
            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        return klaviyoService.getLists(pageSize)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/lists/{listId}/profiles/{profileId}")
    public Mono<ResponseEntity<Map>> addProfileToList(
            @PathVariable String listId,
            @PathVariable String profileId) {
        return klaviyoService.addProfileToList(listId, profileId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // Metrics Endpoints
    @GetMapping("/metrics")
    public Mono<ResponseEntity<Map>> getMetrics() {
        return klaviyoService.getMetrics()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}