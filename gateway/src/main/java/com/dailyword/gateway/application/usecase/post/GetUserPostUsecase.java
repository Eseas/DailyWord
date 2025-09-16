package com.dailyword.gateway.application.usecase.post;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.gateway.dto.post.MyPostPageResponse;

public interface GetUserPostUsecase {
    PageResponse<MyPostPageResponse> getMyPostList(String memberRefCode, int page, int pageSize);
}
