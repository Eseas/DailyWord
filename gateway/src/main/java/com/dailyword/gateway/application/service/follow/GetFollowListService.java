package com.dailyword.gateway.application.service.follow;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.gateway.adapter.out.client.FollowClient;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.follow.GetFollowingListUsecase;
import com.dailyword.gateway.dto.follow.GetFollowingList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFollowListService implements GetFollowingListUsecase {

    private final MemberClient memberClient;
    private final FollowClient followClient;

    @Override
    public PageResponse<GetFollowingList> getFollowList(String memberRefCode, Integer page) {
        Long memberId = memberClient.idByRefCode(memberRefCode).getData();

        return followClient.getFollowingList(memberId, page).getData();
    }
}
