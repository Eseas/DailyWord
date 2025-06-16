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

    private Long memberId;

    private String content;

    private Long likeCount = 0L;

    private Long commentCount = 0L;

    private Long viewCount = 0L;

    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.ACTIVE;

    private Post(Long memberId, String content) {
        this.memberId = memberId;
        this.content = content;
    }

    public static Post create(Long memberId, String content) {
        return new Post(memberId, content);
    }
}
