package com.dailyword.post.facade.dto;

import com.dailyword.post.domain.model.Post;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.repository.projection.PostView;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class PostDetailResponse {
    private final String postRefCode;
    private final String memberRefCode;
    private final String memberNickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final Integer likeCount;
    private final Long commentCount;
    private final Long viewCount;

    private PostDetailResponse(PostView postView) {
        this.postRefCode = postView.getPostRefCode();
        this.memberRefCode = postView.getMemberRefCode();
        this.memberNickname = postView.getMemberNickname();
        this.content = postView.getContent();
        this.createdAt = postView.getCreatedAt();
        this.likeCount = postView.getLikeCount();
        this.commentCount = postView.getCommentCount();
        this.viewCount = postView.getViewCount();
    }

    public static PostDetailResponse toDto(PostView postView) {
        return new PostDetailResponse(postView);
    }
}