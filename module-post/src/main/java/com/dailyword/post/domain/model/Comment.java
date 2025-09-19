package com.dailyword.post.domain.model;

import com.dailyword.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private Long authorId;

    private String content;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    private Comment(Long postId, Long authorId, String content) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.status = CommentStatus.ACTIVE;
    }

    public Comment create(Long postId, Long authorId, String content) {
        return new Comment(postId, authorId, content);
    }

    public void delete() {
        this.status = CommentStatus.DELETED;
    }
}
