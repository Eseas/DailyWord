package com.dailyword.gateway.application.service.follow;

import com.dailyword.gateway.adapter.out.client.FollowClient;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.follow.FollowUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService implements FollowUsecase {

    private final MemberClient memberClient;
    private final FollowClient followClient;

    @Override
    public void follow(String memberRefCode, String followeeRefCode) {
        Long memberId = memberClient.idByRefCode(memberRefCode).getData();
        Long followeeId = memberClient.idByRefCode(followeeRefCode).getData();

        followClient.follow(memberId, followeeId);
        return;
    }
}
