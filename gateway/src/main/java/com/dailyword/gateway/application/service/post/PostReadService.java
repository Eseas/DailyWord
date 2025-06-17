package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.PostReadUsecase;
import com.dailyword.gateway.dto.post.PostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReadService implements PostReadUsecase {

    private final PostClient postClient;

    @Override
    public PostDetailResponse getPost(Long id) {
        return postClient.getPost(id).getData();
    }
}