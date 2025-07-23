package com.dailyword.post.application.usecase.comment;

import com.dailyword.post.adapter.in.facade.dto.CreateCommentRequest;
import com.dailyword.post.adapter.in.facade.dto.CreateCommentResponse;

public interface CreateCommentUsecase {
    CreateCommentResponse createComment(String refCode, CreateCommentRequest request);
}
