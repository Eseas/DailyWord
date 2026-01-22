package com.dailyword.post.infrastructure.kafka;

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
            groupId = "post-service-group",
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
        // TODO: Implement user profile caching or synchronization logic
        // Example: Cache user info for post author display
        log.debug("Processing user created event for post module - userId: {}", event.getUserId());
    }
}
