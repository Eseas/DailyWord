package com.dailyword.member.application.service.post;

import com.dailyword.member.application.usecase.post.PostReadUsecase;
import com.dailyword.member.adapter.in.facade.dto.PostDetailResponse;
import com.dailyword.member.repository.PostRepository;
import com.dailyword.member.repository.projection.PostView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.common.response.ErrorCode.NOT_FOUND_POST;
import static com.dailyword.member.domain.model.PostStatus.*;

@Service
@RequiredArgsConstructor
public class PostReadService implements PostReadUsecase {

    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPost(String postRefCode) {
        PostView post = postRepository.findPostDetailByRefCode(postRefCode, ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));

        return PostDetailResponse.toDto(post);
    }
}
