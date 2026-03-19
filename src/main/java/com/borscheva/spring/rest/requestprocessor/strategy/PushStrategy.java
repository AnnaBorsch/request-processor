package com.borscheva.spring.rest.requestprocessor.strategy;

import com.borscheva.spring.rest.requestprocessor.enums.NotificationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class PushStrategy extends AbstractNotificationStrategy {

    public PushStrategy(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.PUSH;
    }
}