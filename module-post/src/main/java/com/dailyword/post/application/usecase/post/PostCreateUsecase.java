package com.dailyword.post.application.usecase.post;

import com.dailyword.post.application.usecase.command.CreatePostCommand;

public interface PostCreateUsecase {
    String createPost(CreatePostCommand command);
}
