package com.dailyword.gateway.application.usecase.post;

import com.dailyword.gateway.dto.post.PostUpdateRequest;

public interface PostUpdateUsecase {
    String update(String refCode, PostUpdateRequest request);
}
