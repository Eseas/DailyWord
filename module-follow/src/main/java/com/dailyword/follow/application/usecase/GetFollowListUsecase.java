package com.dailyword.follow.application.usecase;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.follow.adapter.in.dto.GetFollowList;

public interface GetFollowListUsecase {
    PageResponse<GetFollowList> getFollowList(Long memberId, Integer page);
}
