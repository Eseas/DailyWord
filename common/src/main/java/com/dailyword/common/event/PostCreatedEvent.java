package com.dailyword.common.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreatedEvent extends DomainEvent {

    private Long postId;
    private Long authorId;
    private String refCode;
    private String content;
    private Boolean isHide;

    @Builder
    public PostCreatedEvent(Long postId, Long authorId, String refCode, String content, Boolean isHide) {
        super("POST_CREATED");
        this.postId = postId;
        this.authorId = authorId;
        this.refCode = refCode;
        this.content = content;
        this.isHide = isHide;
    }

    public static PostCreatedEvent of(Long postId, Long authorId, String refCode, String content, Boolean isHide) {
        return PostCreatedEvent.builder()
                .postId(postId)
                .authorId(authorId)
                .refCode(refCode)
                .content(content)
                .isHide(isHide)
                .build();
    }
}
