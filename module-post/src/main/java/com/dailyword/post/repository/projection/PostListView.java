package com.dailyword.post.repository.projection;

import java.time.LocalDateTime;

public interface PostListView {
    String getPostRefCode();
    String getMemberRefCode();
    String getMemberNickname();
    String getContent();
    LocalDateTime getCreatedAt();
    Integer getLikeCount();
    Long getCommentCount();
    Boolean getIsHide();
}
