package com.dailyword.post.application.service;

import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.application.usecase.PostReadUsecase;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.facade.dto.PostDetailResponse;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.common.response.ErrorCode.NOT_FOUND_POST;

@Service
@RequiredArgsConstructor
public class PostReadService implements PostReadUsecase {

    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPost(String postRefCode) {
        Post post = postRepository.findByRefCode(postRefCode)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));
        return new PostDetailResponse(post);
    }
}
