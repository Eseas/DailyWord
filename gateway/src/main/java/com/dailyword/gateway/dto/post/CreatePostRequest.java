package com.dailyword.gateway.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreatePostRequest {
    private Long authorId;
    private String content;
    private List<String> hashtags;
}
