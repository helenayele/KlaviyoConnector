package org.example.klaviyo.model;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class KlaviyoProfile {
    private String id;
    private String type = "profile";
    private ProfileAttributes attributes;

    @Data
    public static class ProfileAttributes {
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        @JsonProperty("phone_number")
        private String phoneNumber;
        private String organization;
        private String title;
        private Object properties;
    }
}