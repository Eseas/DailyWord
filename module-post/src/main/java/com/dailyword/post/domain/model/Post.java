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

    private Boolean isHide = false;

    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.ACTIVE;

    private Post(Long memberId, String content, boolean isHide) {
        this.authorId = memberId;
        this.content = content;
        this.isHide = isHide;
    }

    public static Post create(Long memberId, String content, boolean isHide) {
        return new Post(memberId, content, isHide);
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateIsHide(Boolean isHide) {
        this.isHide = isHide;
    }

    public void delete() {
        this.status = PostStatus.DELETED;
    }
}
