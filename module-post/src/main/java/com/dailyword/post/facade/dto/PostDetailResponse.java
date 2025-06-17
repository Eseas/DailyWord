package com.dailyword.post.facade.dto;

import com.dailyword.post.domain.model.Post;
import com.dailyword.post.domain.model.PostStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDetailResponse {
    private final Long id;
    private final Long memberId;
    private final String content;
    private final LocalDateTime createdAt;
    private final Integer likeCount;
    private final Long commentCount;
    private final Long viewCount;
    private final PostStatus status;

    public PostDetailResponse(Post post) {
        this.id = post.getId();
        this.memberId = post.getAuthorId();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.viewCount = post.getViewCount();
        this.status = post.getStatus();
    }
}