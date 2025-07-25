package com.dailyword.gateway.application.service.follow;

import com.dailyword.gateway.adapter.out.client.FollowClient;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.follow.GetFollowCountUsecase;
import com.dailyword.gateway.dto.follow.GetFollowCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFollowCountService implements GetFollowCountUsecase {

    private final MemberClient memberClient;
    private final FollowClient followClient;

    @Override
    public GetFollowCount getFollowCount(String memberRefCode) {
        Long memberId = memberClient.idByRefCode(memberRefCode).getData();

        return followClient.getFollowCount(memberId).getData();
    }
}
