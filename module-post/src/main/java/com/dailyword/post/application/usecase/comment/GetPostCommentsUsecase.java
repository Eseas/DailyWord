package com.dailyword.post.application.usecase.comment;

import com.dailyword.post.facade.dto.PostCommentsResponse;

public interface GetPostCommentsUsecase {
    PostCommentsResponse getComments(String refCode, int page, int pageSize);
}
