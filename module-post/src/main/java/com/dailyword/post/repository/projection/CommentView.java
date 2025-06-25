package com.dailyword.post.repository.projection;

import java.time.LocalDateTime;

public interface CommentView {
    Long getCommnetId();
    String getContent();
    String getNickname();
    LocalDateTime getCreatedAt();
}
