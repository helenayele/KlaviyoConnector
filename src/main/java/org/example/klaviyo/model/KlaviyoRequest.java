package org.example.klaviyo.model;

import lombok.Data;

@Data
public class KlaviyoRequest<T> {
    private T data;

    public KlaviyoRequest(T data) {
        this.data = data;
    }
}