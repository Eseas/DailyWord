package com.dailyword.gateway.application.usecase.post;

import com.dailyword.gateway.dto.post.PostDeleteRequest;

public interface PostDeleteUsecase {
    String delete(String refCode, PostDeleteRequest request);
}
