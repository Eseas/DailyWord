package com.dailyword.gateway.application.usecase.follow;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.gateway.dto.follow.GetFollowerList;

public interface GetFollowerListUsecase {
    PageResponse<GetFollowerList> getFollower(String memberRefcode, Integer page);
}
