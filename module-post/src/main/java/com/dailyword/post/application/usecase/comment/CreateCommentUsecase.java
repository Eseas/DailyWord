package com.dailyword.post.application.usecase.comment;

import com.dailyword.post.facade.dto.CreateCommentRequest;
import com.dailyword.post.facade.dto.CreateCommentResponse;

public interface CreateCommentUsecase {
    CreateCommentResponse createComment(CreateCommentRequest request);
}
