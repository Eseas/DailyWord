package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.PostCreateUsecase;
import com.dailyword.gateway.dto.post.CreatePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCreateService implements PostCreateUsecase {

    private final PostClient postClient;
    private final MemberClient memberClient;

    @Override
    public String createPost(CreatePostRequest request) {
        Long memberPK = memberClient.idByRefCode(request.getAuthorRefCode()).getData();

        return postClient.createPost(request.toCommand(memberPK)).getData();
    }
}
