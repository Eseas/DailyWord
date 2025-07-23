package com.dailyword.member.application.usecase.post;

import com.dailyword.member.application.usecase.command.CreatePostCommand;

public interface PostCreateUsecase {
    String createPost(CreatePostCommand command);
}
