package com.dailyword.member.application.usecase.post;


import com.dailyword.member.adapter.in.facade.dto.MyPostPageResponse;

import java.util.List;

public interface UserPostPageUsecase {
    List<MyPostPageResponse> getUserPosts(Long memberId, Integer page, Integer size);
}
