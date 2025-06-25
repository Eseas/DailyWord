package com.dailyword.post.repository;

import com.dailyword.post.domain.model.Post;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.repository.projection.PostView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

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
