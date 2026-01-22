package com.dailyword.notification.infrastructure.kafka;

import com.dailyword.common.event.PostCreatedEvent;
import com.dailyword.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostEventConsumer {

    @KafkaListener(
            topics = KafkaTopics.POST_EVENTS,
            groupId = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        log.info("Received POST_CREATED event - postId: {}, authorId: {}, eventId: {}",
                event.getPostId(),
                event.getAuthorId(),
                event.getEventId());

        try {
            if (Boolean.FALSE.equals(event.getIsHide())) {
                processPostCreatedEvent(event);
                log.info("Successfully processed POST_CREATED event - postId: {}", event.getPostId());
            } else {
                log.info("Skipping notification for hidden post - postId: {}", event.getPostId());
            }
        } catch (Exception e) {
            log.error("Failed to process POST_CREATED event - postId: {}, error: {}",
                    event.getPostId(), e.getMessage(), e);
            throw e;
        }
    }

    private void processPostCreatedEvent(PostCreatedEvent event) {
        // TODO: Implement follower notification logic
        // Example: Fetch followers of authorId and send notifications
        log.debug("Processing post created event for notification - postId: {}, authorId: {}",
                event.getPostId(), event.getAuthorId());
    }
}
