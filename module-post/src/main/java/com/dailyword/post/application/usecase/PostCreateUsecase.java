package com.dailyword.post.application.usecase;

import com.dailyword.post.application.usecase.command.CreatePostCommand;

public interface PostCreateUsecase {
    Long createPost(CreatePostCommand command);
}
