package com.dailyword.gateway.application.usecase.follow;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.gateway.dto.follow.GetFollowingList;

public interface GetFollowingListUsecase {
    PageResponse<GetFollowingList> getFollowList(String memberRefCode, Integer page);
}
