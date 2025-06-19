package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.PostUpdateUsecase;
import com.dailyword.gateway.dto.post.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUpdateService implements PostUpdateUsecase {

    private final PostClient postClient;

    @Override
    public String update(String refCode, PostUpdateRequest request) {
        return postClient.updatePost(refCode, request).getData();
    }
}
