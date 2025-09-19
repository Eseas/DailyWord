package com.dailyword.post.application.usecase.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatePostCommand {
    private final Long authorId;
    private final String content;
    private final Boolean isHide;
    private final List<String> hashtags;
}