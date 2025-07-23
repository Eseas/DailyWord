package com.dailyword.post.adapter.in.facade.dto;

import com.dailyword.post.application.usecase.command.CreatePostCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreatePostRequest {
    private Long authorId;
    private String content;
    private List<String> hashtags;

    public CreatePostCommand toCommand() {
        return new CreatePostCommand(authorId, content, hashtags);
    }
}
