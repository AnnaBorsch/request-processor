package com.borscheva.spring.rest.requestprocessor.service;

import com.borscheva.spring.rest.requestprocessor.dto.NotificationRequest;
import com.borscheva.spring.rest.requestprocessor.entity.NotificationOutbox;
import com.borscheva.spring.rest.requestprocessor.repository.NotificationOutboxRepository;
import com.borscheva.spring.rest.requestprocessor.strategy.NotificationStrategy;
import com.borscheva.spring.rest.requestprocessor.strategy.StrategySelector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationOutboxRepository notificationOutboxRepository;
    private final StrategySelector strategySelector;

    @Transactional
    public void processRequest(NotificationRequest request) {
        log.debug("Processing notification request: type={}, messageLength={}",
                request.getType(),
                request.getMessage().length());

        NotificationStrategy strategy = strategySelector.getStrategy(request.getType());

        String key = UUID.randomUUID().toString();
        String payload = strategy.preparePayload(request.getMessage());
        String topic = strategy.getTopic();

        NotificationOutbox outbox = NotificationOutbox.builder()
                .messageKey(key)
                .topic(topic)
                .payload(payload)
                .sent(false)
                .attempt(0)
                .build();

        notificationOutboxRepository.save(outbox);

        log.info("Подготовлено сообщение для отправки. Key: {}, Payload: {}, topic: {}",
                key, payload, topic);
    }
}