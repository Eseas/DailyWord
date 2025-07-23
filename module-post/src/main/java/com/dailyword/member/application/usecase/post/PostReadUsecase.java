package com.dailyword.member.application.usecase.post;

import com.dailyword.member.adapter.in.facade.dto.PostDetailResponse;

public interface PostReadUsecase {
    PostDetailResponse getPost(String postRefCode);
}
