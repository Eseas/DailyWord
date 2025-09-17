package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.PostHideUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostHideService implements PostHideUsecase {

    private final PostClient postClient;

    @Override
    public String hidePost(String postRefCode) {
        return postClient.hidePost(postRefCode).getData();
    }
}