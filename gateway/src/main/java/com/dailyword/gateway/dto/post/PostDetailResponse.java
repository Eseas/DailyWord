package com.dailyword.gateway.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostDetailResponse {
    private Long id;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;
    private Integer likeCount;
    private Long commentCount;
    private Long viewCount;
    private String status;
}
