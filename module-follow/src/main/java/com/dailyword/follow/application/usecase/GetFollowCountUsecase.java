package com.dailyword.follow.application.usecase;

import com.dailyword.follow.adapter.in.dto.GetFollowingCount;

public interface GetFollowCountUsecase {
    GetFollowingCount getFollowingCount(Long memberId);
}
