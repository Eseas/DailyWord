package com.dailyword.gateway.application.usecase.post;

import com.dailyword.gateway.dto.post.CreatePostRequest;

public interface PostCreateUsecase {
    String createPost(CreatePostRequest request);
}
