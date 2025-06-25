package com.dailyword.post.repository.projection;

import com.dailyword.post.domain.model.PostStatus;

import java.time.LocalDateTime;

public interface PostView {
    String getPostRefCode();
    String getMemberRefCode();
    String getMemberNickname();
    String getContent();
    LocalDateTime getCreatedAt();
    Integer getLikeCount();
    Long getCommentCount();
    Long getViewCount();
    PostStatus getStatus();
}
