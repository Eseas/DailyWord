package com.dailyword.post.application.usecase.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatePostCommand {
    private final Long authorId;
    private final String content;
    private final List<String> hashtags;
}