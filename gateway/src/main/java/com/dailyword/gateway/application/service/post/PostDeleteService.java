package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.PostDeleteUsecase;
import com.dailyword.gateway.dto.post.PostDeleteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDeleteService implements PostDeleteUsecase {

    private final PostClient postClient;

    @Override
    public String delete(String refCode, PostDeleteRequest request) {
        return postClient.deletePost(refCode, request).getData();
    }
}
