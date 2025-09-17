package com.dailyword.member.application.service.post;

import com.dailyword.member.application.usecase.post.PostHideUsecase;
import com.dailyword.member.domain.model.Post;
import com.dailyword.member.domain.model.PostStatus;
import com.dailyword.member.repository.PostRepository;
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