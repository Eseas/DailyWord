package com.dailyword.member.repository.projection;

import java.time.LocalDateTime;

public interface CommentView {
    Long getCommnetId();
    String getContent();
    String getNickname();
    LocalDateTime getCreatedAt();
}
