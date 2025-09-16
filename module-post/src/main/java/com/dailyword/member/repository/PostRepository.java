package com.dailyword.member.repository;

import com.dailyword.member.domain.model.Post;
import com.dailyword.member.domain.model.PostStatus;
import com.dailyword.member.repository.projection.PostListView;
import com.dailyword.member.repository.projection.PostView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.refCode = :refCode AND p.status = :postStatus")
    Optional<Post> findByRefCodeAndStatus(String refCode, PostStatus postStatus);

    @Query("""
    SELECT
        p.refCode AS PostRefCode,
        m.refCode AS MemberRefCode,
        m.nickname AS MemberNickname,
        p.content AS Content,
        p.createdAt AS CreatedAt,
        p.likeCount AS LikeCount,
        p.commentCount AS CommentCount
    FROM
        Post p
    LEFT JOIN Member m ON p.authorId = m.id
    WHERE p.authorId = :memberId
      AND p.status = :status
    ORDER BY p.createdAt DESC
    """)
    Page<PostListView> findByMemberIdAndStatus(Pageable pageable, Long memberId, PostStatus status);

    @Query("""
        SELECT
            p.refCode AS PostRefCode,
            m.refCode AS MemberRefCode,
            m.nickname AS MemberNickname,
            p.content AS Content,
            p.createdAt AS CreatedAt,
            p.likeCount AS LikeCount,
            p.commentCount AS CommentCount
        FROM
            Post p
        LEFT JOIN Member m ON m.id = p.authorId
        WHERE
            p.refCode = :refCode
        AND p.status = :status
    """)
    Optional<PostView> findPostDetailByRefCode(String refCode, PostStatus status);
}
