package com.borscheva.spring.rest.requestprocessor.strategy;

import com.borscheva.spring.rest.requestprocessor.enums.NotificationType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StrategySelector {

    private final Map<NotificationType, NotificationStrategy> strategies;

    public StrategySelector(List<NotificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        NotificationStrategy::getType,
                        strategy -> strategy
                ));
    }

    public NotificationStrategy getStrategy(NotificationType type) {
        return Optional.ofNullable(strategies.get(type))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown notification type: " + type));
    }
}