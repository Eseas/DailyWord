package com.dailyword.post.application.usecase.comment;

import com.dailyword.post.adapter.in.facade.dto.PostCommentsResponse;

public interface GetPostCommentsUsecase {
    PostCommentsResponse getComments(String refCode, int page, int pageSize);
}
