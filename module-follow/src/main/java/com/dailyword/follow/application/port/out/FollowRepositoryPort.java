package com.dailyword.follow.application.port.out;

import com.dailyword.follow.domain.constant.FollowStatus;
import com.dailyword.follow.domain.model.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FollowRepositoryPort {

    Follow save(Follow follow);

    Optional<Follow> findFollow(Long followerId, Long followeeId, FollowStatus status);

    Long countFollowers(Long memberId, FollowStatus status);

    Long countFollowees(Long memberId, FollowStatus status);

    Page<Follow> findFollowerPage(Long followeeId, FollowStatus status, Pageable pageable);

    Page<Follow> findFolloweePage(Long followerId, FollowStatus status, Pageable pageable);
}
