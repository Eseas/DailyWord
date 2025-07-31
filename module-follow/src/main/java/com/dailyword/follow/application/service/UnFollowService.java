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

@Service
@RequiredArgsConstructor
public class UnFollowService implements UnFollowUsecase {

    private final FollowRepository followRepository;

    @Override
    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        Follow follow = followRepository.findFollow(followerId, followeeId, FOLLOWING)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOLLOWING.getMessage()));

        follow.unfollow();
    }
}
