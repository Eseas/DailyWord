package com.dailyword.member.application.usecase.comment;

import com.dailyword.member.adapter.in.facade.dto.CreateCommentRequest;
import com.dailyword.member.adapter.in.facade.dto.CreateCommentResponse;

public interface CreateCommentUsecase {
    CreateCommentResponse createComment(String refCode, CreateCommentRequest request);
}
