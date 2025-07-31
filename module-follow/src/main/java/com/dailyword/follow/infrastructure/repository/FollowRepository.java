package com.dailyword.follow.infrastructure.repository;

import com.dailyword.follow.domain.constant.FollowStatus;
import com.dailyword.follow.domain.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("""
    SELECT count(f.followerId) FROM Follow f WHERE f.followerId = :memberId and f.followStatus = :followStatus
    """)
    Long getFollowerCount(@Param("memberId") Long memberId, FollowStatus followStatus);

    @Query("""
    SELECT count(f.followerId) FROM Follow f WHERE f.followeeId = :memberId and f.followStatus = :followStatus
    """)
    Long getFolloweeCount(@Param("memberId") Long memberId, FollowStatus followStatus);

    @Query("""
    SELECT f FROM Follow f WHERE f.followerId = :followerId AND f.followeeId = :followeeId AND f.followStatus = :followStatus
    """)
    Optional<Follow> findFollow(@Param("followerId") Long followerId,
                                @Param("followeeId") Long followeeId,
                                @Param("followStatus") FollowStatus followStatus
    );
}
