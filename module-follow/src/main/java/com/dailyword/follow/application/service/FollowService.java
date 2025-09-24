package com.dailyword.follow.application.service;

import com.dailyword.follow.application.usecase.FollowUsecase;
import com.dailyword.follow.domain.constant.FollowStatus;
import com.dailyword.follow.domain.model.Follow;
import com.dailyword.follow.infrastructure.repository.FollowRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.dailyword.follow.domain.constant.FollowStatus.*;

/**
 * 팔로우 서비스
 * 사용자 간 팔로우 관계를 생성하고 관리하는 비즈니스 로직을 담당합니다.
 * 기존 언팔로우 상태의 관계가 있다면 재활성화하고, 없다면 새로 생성합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class FollowService implements FollowUsecase {

    private final FollowRepository followRepository;

    /**
     * 사용자 팔로우
     * 특정 사용자를 팔로우합니다. 이미 언팔로우 상태의 관계가 존재하는 경우 재활성화하고,
     * 관계가 없는 경우 새로운 팔로우 관계를 생성합니다.
     *
     * @param memberId 팔로우를 수행할 사용자의 ID
     * @param followeeId 팔로우 대상 사용자의 ID
     */
    @Override
    @Transactional
    public void follow(Long memberId, Long followeeId) {
        Optional<Follow> existingFollow = followRepository.findFollow(memberId, followeeId, UNFOLLOWING);

        if (existingFollow.isPresent()) {
            existingFollow.get().follow();
        } else {
            Follow follow = Follow.follow(memberId, followeeId);
            followRepository.save(follow);
        }
    }
}
