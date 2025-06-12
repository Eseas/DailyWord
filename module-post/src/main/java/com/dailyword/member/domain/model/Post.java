package com.dailyword.member.domain.model;

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

    private Long likeCount;

    private Long commentCount;

    private Long viewCount;

    @Enumerated(EnumType.STRING)
    private PostStatus status;
}
