package com.dailyword.follow.adapter.out.persistence;

import com.dailyword.follow.application.port.out.FollowRepositoryPort;
import com.dailyword.follow.domain.constant.FollowStatus;
import com.dailyword.follow.domain.model.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FollowPersistenceAdapter implements FollowRepositoryPort {

    private final FollowJpaRepository followJpaRepository;

    @Override
    public Follow save(Follow follow) {
        return followJpaRepository.save(follow);
    }

    @Override
    public Optional<Follow> findFollow(Long followerId, Long followeeId, FollowStatus status) {
        return followJpaRepository.findFollow(followerId, followeeId, status);
    }

    @Override
    public Long countFollowers(Long memberId, FollowStatus status) {
        return followJpaRepository.getFollowerCount(memberId, status);
    }

    @Override
    public Long countFollowees(Long memberId, FollowStatus status) {
        return followJpaRepository.getFolloweeCount(memberId, status);
    }

    @Override
    public Page<Follow> findFollowerPage(Long followeeId, FollowStatus status, Pageable pageable) {
        return followJpaRepository.findFollowPageByFolloweeId(followeeId, status, pageable);
    }

    @Override
    public Page<Follow> findFolloweePage(Long followerId, FollowStatus status, Pageable pageable) {
        return followJpaRepository.findFollowPageByFollowerId(followerId, status, pageable);
    }
}
