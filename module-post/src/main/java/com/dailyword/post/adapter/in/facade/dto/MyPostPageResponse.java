package com.dailyword.post.adapter.in.facade.dto;

import com.dailyword.post.repository.projection.PostListView;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPostPageResponse {
    private final String postRefCode;
    private final String authorRefCode;
    private final String authorNickname;
    private final String contentPreview;
    private final LocalDateTime createdAt;
    private final int likeCount;

    private MyPostPageResponse(String postRefCode, String authorRefCode, String authorNickname, String contentPreview,
                               LocalDateTime createdAt, int likeCount) {
        this.postRefCode = postRefCode;
        this.authorRefCode = authorRefCode;
        this.authorNickname = authorNickname;
        this.contentPreview = contentPreview;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    public static MyPostPageResponse toDto(PostListView post) {
        return new MyPostPageResponse(post.getPostRefCode(), post.getMemberRefCode(), post.getMemberNickname(), summarize(post.getContent()), post.getCreatedAt(), post.getLikeCount());
    }

    private static String summarize(String content) {
        return content.length() > 100 ? content.substring(0, 100) + "..." : content;
    }
}
