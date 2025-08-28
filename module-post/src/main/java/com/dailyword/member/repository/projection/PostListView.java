package com.dailyword.member.repository.projection;

import com.dailyword.member.domain.model.PostStatus;

import java.time.LocalDateTime;

public interface PostListView {
    String getPostRefCode();
    String getMemberRefCode();
    String getMemberNickname();
    String getContent();
    LocalDateTime getCreatedAt();
    Integer getLikeCount();
    Long getCommentCount();
}
