package com.dailyword.common.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class DomainEvent {

    private String eventId;
    private String eventType;
    private LocalDateTime occurredAt;

    protected DomainEvent(String eventType) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.occurredAt = LocalDateTime.now();
    }
}
