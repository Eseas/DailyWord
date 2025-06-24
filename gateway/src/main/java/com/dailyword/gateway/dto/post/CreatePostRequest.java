package com.dailyword.gateway.dto.post;

import com.dailyword.gateway.application.service.post.command.PostCreateCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreatePostRequest {
    private String authorRefCode;
    private String content;
    private List<String> hashtags;

    public PostCreateCommand toCommand(Long authorId) {
        return new PostCreateCommand(authorId, this.content, this.hashtags);
    }
}
