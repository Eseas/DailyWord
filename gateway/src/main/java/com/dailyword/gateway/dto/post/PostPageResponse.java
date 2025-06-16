package com.dailyword.gateway.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostPageResponse {
    private Long id;
    private Long authorId;
    private String contentPreview;
    private LocalDateTime createdAt;
    private Integer likeCount;
    private List<String> hashtags;
}
