package com.dailyword.gateway.application.service.post;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.GetUserPostUsecase;
import com.dailyword.gateway.dto.post.MyPostPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class getUserPostList implements GetUserPostUsecase {

    private final MemberClient memberClient;
    private final PostClient postClient;

    @Override
    public PageResponse<MyPostPageResponse> getMyPostList(String memberRefCode, int page, int pageSize) {
        Long memberId = memberClient.idByRefCode(memberRefCode).getData();

        PageResponse<MyPostPageResponse> usersPost = postClient.getUserPosts(memberId, page, pageSize).getData();

        return usersPost;
    }
}
