package com.dailyword.follow.adapter.in.dto;

import com.dailyword.follow.domain.model.Follow;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetFollowList {
    Long followId;

    private GetFollowList(Long followId) {
        this.followId = followId;
    }

    public static GetFollowList create(Follow follow) {
        return new GetFollowList(follow.getId());
    }
}
