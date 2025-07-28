package com.dailyword.gateway.application.service.follow;

import com.dailyword.gateway.adapter.out.client.FollowClient;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.follow.UnFollowUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnFollowService implements UnFollowUsecase {

    private final MemberClient memberClient;
    private final FollowClient followClient;

    @Override
    public void unFollow(String followerRefCode, String followeeRefCode) {
        Long followerId = memberClient.idByRefCode(followerRefCode).getData();
        Long followeeId = memberClient.idByRefCode(followeeRefCode).getData();

        followClient.unFollow(followerId, followeeId);

        return;
    }
}
