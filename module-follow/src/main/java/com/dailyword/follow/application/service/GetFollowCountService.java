package com.dailyword.follow.application.service;

import com.dailyword.follow.adapter.in.dto.GetFollowingCount;
import com.dailyword.follow.application.usecase.GetFollowCountUsecase;
import com.dailyword.follow.domain.constant.FollowStatus;
import com.dailyword.follow.infrastructure.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.follow.domain.constant.FollowStatus.FOLLOWING;

/**
 * 사용자의 팔로워 및 팔로잉 수를 조회하는 서비스입니다.
 *
 * 특정 사용자의 팔로워 수와 팔로잉 수를 데이터베이스에서 조회하여 반환합니다.
 * FOLLOWING 상태인 관계만을 대상으로 하여 정확한 수치를 제공합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GetFollowCountService implements GetFollowCountUsecase {

    private final FollowRepository followRepository;

    /**
     * 특정 사용자의 팔로워 수와 팔로잉 수를 조회합니다.
     *
     * 데이터베이스에서 해당 사용자를 팔로우하는 사용자 수와
     * 해당 사용자가 팔로우하고 있는 사용자 수를 조회하여 반환합니다.
     * FOLLOWING 상태인 관계만을 대상으로 합니다.
     *
     * @param memberId 조회 대상 사용자의 ID
     * @return 팔로워 수와 팔로잉 수 정보
     */
    @Override
    @Transactional(readOnly = true)
    public GetFollowingCount getFollowingCount(Long memberId) {
        Long followerCount = followRepository.getFollowerCount(memberId, FOLLOWING);
        Long followeeCount = followRepository.getFolloweeCount(memberId, FOLLOWING);
        return GetFollowingCount.create(followerCount, followeeCount);
    }
}
