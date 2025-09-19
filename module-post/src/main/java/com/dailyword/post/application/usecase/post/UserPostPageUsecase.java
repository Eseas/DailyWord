package com.dailyword.post.application.usecase.post;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.post.adapter.in.facade.dto.MyPostPageResponse;

public interface UserPostPageUsecase {
    PageResponse<MyPostPageResponse> getUserPosts(Long memberId, Integer page, Integer size);
}
