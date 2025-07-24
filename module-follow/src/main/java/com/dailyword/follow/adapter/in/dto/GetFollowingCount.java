package com.dailyword.follow.adapter.in.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetFollowingCount {
    Long followerCount;
    Long followingCount;

    private GetFollowingCount(Long followerCount, Long followingCount) {
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public static GetFollowingCount create(Long followerCount, Long followingCount) {
        return new GetFollowingCount(followerCount, followingCount);
    }
}
