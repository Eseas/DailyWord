package com.dailyword.gateway.application.usecase.comment;

import com.dailyword.gateway.dto.comment.CreateCommentRequest;
import com.dailyword.gateway.dto.comment.CreateCommentResponse;

public interface CreateCommentUsecase {
    CreateCommentResponse createComment(String refCode, CreateCommentRequest request);
}
