package com.dailyword.follow.application.service;

import com.dailyword.follow.adapter.in.dto.GetFollowingCount;
import com.dailyword.follow.application.usecase.GetFollowCountUsecase;
import com.dailyword.follow.domain.constant.FollowStatus;
import com.dailyword.follow.infrastructure.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.dailyword.follow.domain.constant.FollowStatus.FOLLOWING;

@Service
@RequiredArgsConstructor
public class GetFollowCountService implements GetFollowCountUsecase {

    private final FollowRepository followRepository;

    @Override
    public GetFollowingCount getFollowingCount(Long memberId) {
        Long followerCount = followRepository.getFollowerCount(memberId, FOLLOWING);
        Long followeeCount = followRepository.getFolloweeCount(memberId, FOLLOWING);
        return GetFollowingCount.create(followerCount, followeeCount);
    }
}
