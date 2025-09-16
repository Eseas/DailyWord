package com.dailyword.member.application.usecase.post;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.member.adapter.in.facade.dto.MyPostPageResponse;

public interface UserPostPageUsecase {
    PageResponse<MyPostPageResponse> getUserPosts(Long memberId, Integer page, Integer size);
}
