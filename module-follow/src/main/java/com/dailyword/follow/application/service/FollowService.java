package com.dailyword.follow.application.service;

import com.dailyword.follow.application.usecase.FollowUsecase;
import com.dailyword.follow.domain.model.Follow;
import com.dailyword.follow.infrastructure.repository.FollowRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService implements FollowUsecase {

    private final FollowRepository followRepository;

    @Override
    @Transactional
    public void follow(Long memberId, Long followeeId) {
        Follow follow = Follow.follow(memberId, followeeId);

        followRepository.save(follow);
        return;
    }
}
