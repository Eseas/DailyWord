package com.dailyword.gateway.dto.follow;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetFollowCount {
    String memberRefCode;
    Long followerCount;
    Long followingCount;

    private GetFollowCount(String memberRefCode, Long followerCount, Long followingCount) {
        this.memberRefCode = memberRefCode;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public static GetFollowCount create(String memberRefCode, Long followerCount, Long followingCount) {
        return new GetFollowCount(memberRefCode, followerCount, followingCount);
    }
}
