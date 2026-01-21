package com.dailyword.follow.application.service;

import com.dailyword.follow.adapter.in.dto.GetFollowingCount;
import com.dailyword.follow.application.port.out.FollowRepositoryPort;
import com.dailyword.follow.application.usecase.GetFollowCountUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.follow.domain.constant.FollowStatus.FOLLOWING;

@Service
@RequiredArgsConstructor
public class GetFollowCountService implements GetFollowCountUsecase {

    private final FollowRepositoryPort followRepositoryPort;

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
        Long followerCount = followRepositoryPort.countFollowers(memberId, FOLLOWING);
        Long followeeCount = followRepositoryPort.countFollowees(memberId, FOLLOWING);
        return GetFollowingCount.create(followerCount, followeeCount);
    }
}
