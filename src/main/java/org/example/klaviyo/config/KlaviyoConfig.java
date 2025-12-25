package org.example.klaviyo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class KlaviyoConfig {

    @Value("${klaviyo.api.base-url}")
    private String baseUrl;

    @Value("${klaviyo.api.api-key}")
    private String apiKey;

    @Value("${klaviyo.api.revision}")
    private String revision;

    @Bean
    public WebClient klaviyoWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Klaviyo-API-Key " + apiKey)
                .defaultHeader("revision", revision)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
