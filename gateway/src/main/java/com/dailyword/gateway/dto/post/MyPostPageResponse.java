package com.dailyword.gateway.dto.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class MyPostPageResponse {
    private final String postRefCode;
    private final String authorRefCode;
    private final String authorNickname;
    private final String contentPreview;
    private final LocalDateTime createdAt;
    private final int likeCount;
}
