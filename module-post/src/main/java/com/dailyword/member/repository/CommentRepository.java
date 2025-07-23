package com.dailyword.member.repository;

import com.dailyword.member.domain.model.Comment;
import com.dailyword.member.domain.model.CommentStatus;
import com.dailyword.member.repository.projection.CommentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
        SELECT
            c.id AS commentId,
            c.content AS content,
            m.nickname AS nickname,
            c.createdAt AS createdAt
        FROM
            Comment c
        LEFT JOIN Member m ON m.id = c.authorId
        WHERE
            c.postId = :postId
        AND c.status = :status
    """)
    Page<CommentView> getCommentsByPostId(Pageable pageable, Long postId, CommentStatus status);

    @Query("""
        SELECT 
            c
        FROM Comment c
        WHERE c.id = :commentId
          AND c.postId = :postId
          AND c.status = :status
    """)
    Optional<Comment> findByCommentIdAndPostIdAndStatus(Long commentId, Long postId, CommentStatus status);
}
