package com.borscheva.spring.rest.requestprocessor.strategy;

import com.borscheva.spring.rest.requestprocessor.enums.NotificationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class EmailStrategy extends AbstractNotificationStrategy {

    public EmailStrategy(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
}