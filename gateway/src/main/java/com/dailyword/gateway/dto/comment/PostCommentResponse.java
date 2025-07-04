package com.dailyword.gateway.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostCommentResponse {
    Long postId;
    List<Comment> comments;

    @Getter
    @AllArgsConstructor
    public class Comment {
        Long commentId;
        String content;
        String nickname;
        LocalDateTime createdAt;
    }
}
