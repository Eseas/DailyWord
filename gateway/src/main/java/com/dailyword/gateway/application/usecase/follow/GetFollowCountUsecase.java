package com.dailyword.gateway.application.usecase.follow;

import com.dailyword.gateway.dto.follow.GetFollowCount;

public interface GetFollowCountUsecase {
    GetFollowCount getFollowCount(String memberRefCode);
}
