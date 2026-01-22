package com.dailyword.common.kafka;

import com.dailyword.common.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;

    public void publish(String topic, DomainEvent event) {
        publish(topic, event.getEventId(), event);
    }

    public void publish(String topic, String key, DomainEvent event) {
        CompletableFuture<SendResult<String, DomainEvent>> future = kafkaTemplate.send(topic, key, event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Event published successfully - topic: {}, eventType: {}, eventId: {}, partition: {}, offset: {}",
                        topic,
                        event.getEventType(),
                        event.getEventId(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Failed to publish event - topic: {}, eventType: {}, eventId: {}, error: {}",
                        topic,
                        event.getEventType(),
                        event.getEventId(),
                        ex.getMessage());
            }
        });
    }
}
