package com.dailyword.gateway.application.usecase.comment;

import com.dailyword.gateway.dto.comment.PostCommentResponse;

public interface GetPostCommentUsecase {
    PostCommentResponse getComments(String postRefCode, Integer page, Integer pageSize);
}
