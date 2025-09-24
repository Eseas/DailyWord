package com.dailyword.follow.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.follow.application.usecase.UnFollowUsecase;
import com.dailyword.follow.domain.constant.FollowStatus;
import com.dailyword.follow.domain.model.Follow;
import com.dailyword.follow.infrastructure.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.follow.domain.constant.FollowStatus.*;

/**
 * 사용자 간의 팔로우 관계를 해제하는 서비스입니다.
 *
 * 기존의 FOLLOWING 상태인 팔로우 관계를 조회하여 언팔로우 상태로 변경합니다.
 * 팔로운 관계가 없는 경우 비즈니스 예외를 발생시킵니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UnFollowService implements UnFollowUsecase {

    private final FollowRepository followRepository;

    /**
     * 지정된 두 사용자 간의 팔로우 관계를 해제합니다.
     *
     * 팔로우어와 팔로우이 간의 FOLLOWING 상태인 관계를 조회하여
     * 언팔로우 상태로 변경합니다. 팔로우 관계가 존재하지 않는 경우
     * 비즈니스 예외를 발생시킵니다.
     *
     * @param followerId 팔로우를 해제할 사용자의 ID (팔로우어)
     * @param followeeId 팔로우 해제 대상 사용자의 ID (팔로우이)
     * @throws BusinessException 팔로우 관계가 없는 경우 발생
     */
    @Override
    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        Follow follow = followRepository.findFollow(followerId, followeeId, FOLLOWING)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOLLOWING.getMessage()));

        follow.unfollow();
    }
}
