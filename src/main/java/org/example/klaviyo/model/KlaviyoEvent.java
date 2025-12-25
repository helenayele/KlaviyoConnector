package org.example.klaviyo.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@Data
public class KlaviyoEvent {
    private String type = "event";
    private EventAttributes attributes;

    @Data
    public static class EventAttributes {
        private Map<String, Object> properties;
        private Map<String, Object> metric;
        private Map<String, Object> profile;
        private String time;
        private Double value;
        @JsonProperty("unique_id")
        private String uniqueId;
    }
}