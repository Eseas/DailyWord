package com.dailyword.post.application.service;

import com.dailyword.post.application.usecase.PostReadUsecase;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.facade.dto.PostDetailResponse;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostReadService implements PostReadUsecase {

    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPost(String postRefCode) {
        Post post = postRepository.findByRefCode(postRefCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return new PostDetailResponse(post);
    }
}
