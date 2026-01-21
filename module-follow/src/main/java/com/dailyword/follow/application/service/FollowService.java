package com.dailyword.follow.application.service;

import com.dailyword.follow.application.port.out.FollowRepositoryPort;
import com.dailyword.follow.application.usecase.FollowUsecase;
import com.dailyword.follow.domain.model.Follow;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.dailyword.follow.domain.constant.FollowStatus.*;

@Service
@RequiredArgsConstructor
public class FollowService implements FollowUsecase {

    private final FollowRepositoryPort followRepositoryPort;

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
        Optional<Follow> existingFollow = followRepositoryPort.findFollow(memberId, followeeId, UNFOLLOWING);

        if (existingFollow.isPresent()) {
            existingFollow.get().follow();
        } else {
            Follow follow = Follow.follow(memberId, followeeId);
            followRepositoryPort.save(follow);
        }
    }
}
