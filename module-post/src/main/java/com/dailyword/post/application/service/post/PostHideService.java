package com.dailyword.post.application.service.post;

import com.dailyword.post.application.usecase.post.PostHideUsecase;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.common.response.ErrorCode.NOT_FOUND_POST;

@Service
@RequiredArgsConstructor
public class PostHideService implements PostHideUsecase {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public String hidePost(String postRefCode) {
        Post post = postRepository.findByRefCodeAndStatus(postRefCode, PostStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));

        post.updateIsHide(!post.getIsHide());
        return post.getRefCode();
    }
}