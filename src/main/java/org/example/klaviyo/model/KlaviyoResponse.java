package org.example.klaviyo.model;

import lombok.Data;
import java.util.List;

@Data
public class KlaviyoResponse<T> {
    private T data;
    private List<T> dataList;
    private Links links;

    @Data
    public static class Links {
        private String self;
        private String next;
        private String prev;
    }
}