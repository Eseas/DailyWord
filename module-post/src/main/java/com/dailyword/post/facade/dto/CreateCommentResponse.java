package com.dailyword.post.facade.dto;

import com.dailyword.post.domain.model.Comment;

public class CreateCommentResponse {
    private Long commentId;
    private String content;

    private CreateCommentResponse(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }

    public static CreateCommentResponse toDto(Comment comment) {
        return new CreateCommentResponse(comment.getId(), comment.getContent());
    }
}
