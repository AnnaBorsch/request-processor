package com.borscheva.spring.rest.requestprocessor.scheduler;

import com.borscheva.spring.rest.requestprocessor.entity.NotificationOutbox;
import com.borscheva.spring.rest.requestprocessor.repository.NotificationOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final NotificationOutboxRepository notificationOutboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${outbox.batch-size}")
    private int batchSize;

    @Value("${outbox.delay-ms}")
    private long delayMs;

    @Value("${outbox.max-attempts}")
    private int maxAttempts;

    @Scheduled(fixedDelayString = "${outbox.delay-ms}")
    @Transactional
    public void sendPendingMessages() {
        Pageable pageable = PageRequest.of(0, batchSize);

        List<NotificationOutbox> messages = notificationOutboxRepository
                .findUnsentMessages(maxAttempts, pageable);

        if (messages.isEmpty()) {
            return;
        }

        log.info("Found {} pending messages to send", messages.size());

        for (NotificationOutbox msg : messages) {
            try {
                kafkaTemplate.send(msg.getTopic(), msg.getMessageKey(), msg.getPayload()).get();

                notificationOutboxRepository.markAsSent(msg.getId());

                log.info("Message sent successfully. Key: {}, Topic: {}",
                        msg.getMessageKey(), msg.getTopic());

            } catch (Exception e) {
                notificationOutboxRepository.incrementAttempt(msg.getId());

                log.error("Failed to send message. Key: {}, Attempt: {}, Error: {}",
                        msg.getMessageKey(),
                        msg.getAttempt() + 1,
                        e.getMessage());
            }
        }
    }
}