package com.dailyword.member.application.usecase.comment;

import com.dailyword.member.adapter.in.facade.dto.PostCommentsResponse;

public interface GetPostCommentsUsecase {
    PostCommentsResponse getComments(String refCode, int page, int pageSize);
}
