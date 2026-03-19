package com.borscheva.spring.rest.requestprocessor.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNotificationStrategy implements NotificationStrategy {

    protected final ObjectMapper objectMapper;

    protected AbstractNotificationStrategy(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String getTopic() {
        return getType().name().toLowerCase() + "-events";
    }

    @Override
    public String preparePayload(String message) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("type", getType().name());
            payload.put("content", message);
            payload.put("timestamp", System.currentTimeMillis());
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to prepare payload for " + getType(), e);
        }
    }
}