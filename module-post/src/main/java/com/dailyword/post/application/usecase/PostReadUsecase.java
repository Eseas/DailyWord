package com.dailyword.post.application.usecase;

import com.dailyword.post.facade.dto.PostDetailResponse;

public interface PostReadUsecase {
    PostDetailResponse getPost(String postRefCode);
}
