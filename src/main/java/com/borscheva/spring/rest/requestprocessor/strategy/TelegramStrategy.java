package com.borscheva.spring.rest.requestprocessor.strategy;

import com.borscheva.spring.rest.requestprocessor.enums.NotificationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class TelegramStrategy extends AbstractNotificationStrategy {

    public TelegramStrategy(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.TG_MESSAGE;
    }

    @Override
    public String getTopic() {
        return "telegram-events";
    }
}