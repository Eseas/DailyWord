package com.dailyword.post.application.usecase.post;

import com.dailyword.post.facade.dto.PostDetailResponse;

public interface PostReadUsecase {
    PostDetailResponse getPost(String postRefCode);
}
