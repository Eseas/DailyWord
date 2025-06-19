package com.dailyword.post.domain.model;

import com.dailyword.common.domain.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="post")
public class Post extends BaseUuidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;

    private String content;

    private Integer likeCount = 0;

    private Long commentCount = 0L;

    private Long viewCount = 0L;

    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.ACTIVE;

    private Post(Long memberId, String content) {
        this.authorId = memberId;
        this.content = content;
    }

    public static Post create(Long memberId, String content) {
        return new Post(memberId, content);
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.status = PostStatus.DELETED;
    }
}
