package com.dailyword.gateway.application.usecase.post;

import com.dailyword.gateway.dto.post.PostDetailResponse;

public interface PostReadUsecase {
    PostDetailResponse getPost(String postRefCode);
}
