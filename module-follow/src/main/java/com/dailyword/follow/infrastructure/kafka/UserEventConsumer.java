package com.dailyword.follow.infrastructure.kafka;

import com.dailyword.common.event.UserCreatedEvent;
import com.dailyword.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    @KafkaListener(
            topics = KafkaTopics.USER_EVENTS,
            groupId = "follow-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("Received USER_CREATED event - userId: {}, nickname: {}, eventId: {}",
                event.getUserId(),
                event.getNickname(),
                event.getEventId());

        try {
            processUserCreatedEvent(event);
            log.info("Successfully processed USER_CREATED event - userId: {}", event.getUserId());
        } catch (Exception e) {
            log.error("Failed to process USER_CREATED event - userId: {}, error: {}",
                    event.getUserId(), e.getMessage(), e);
            throw e;
        }
    }

    private void processUserCreatedEvent(UserCreatedEvent event) {
        // TODO: Implement initial follow settings or recommendations
        // Example: Set up default follow list or recommend users to follow
        log.debug("Processing user created event for follow module - userId: {}", event.getUserId());
    }
}
