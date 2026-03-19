package com.borscheva.spring.rest.requestprocessor.strategy;

import com.borscheva.spring.rest.requestprocessor.enums.NotificationType;

public interface NotificationStrategy {

    String getTopic();

    String preparePayload(String message);

    NotificationType getType();
}