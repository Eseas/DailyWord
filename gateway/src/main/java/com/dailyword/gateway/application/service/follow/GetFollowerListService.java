package com.dailyword.gateway.application.service.follow;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.gateway.adapter.out.client.FollowClient;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.follow.GetFollowerListUsecase;
import com.dailyword.gateway.dto.follow.GetFollowerList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFollowerListService implements GetFollowerListUsecase {

    private final MemberClient memberClient;
    private final FollowClient followClient;

    @Override
    public PageResponse<GetFollowerList> getFollower(String memberRefcode, Integer page) {
        Long memberId = memberClient.idByRefCode(memberRefcode).getData();

        return followClient.getFollowerList(memberId, page).getData();
    }
}
