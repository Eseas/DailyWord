package com.dailyword.post.repository;

import com.dailyword.post.domain.model.Comment;
import com.dailyword.post.domain.model.CommentStatus;
import com.dailyword.post.repository.projection.CommentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
