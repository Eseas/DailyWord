package com.dailyword.post.application.service;

import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.application.usecase.PostUpdateUsecase;
import com.dailyword.post.application.usecase.command.UpdatePostCommand;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.common.response.ErrorCode.NOT_FOUND_POST;
import static com.dailyword.common.response.ErrorCode.NOT_YOUR_POST;

@Service
@RequiredArgsConstructor
public class PostUpdateService implements PostUpdateUsecase {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public String update(UpdatePostCommand command) {
        Post post = postRepository.findByRefCode(command.getRefCode())
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));

        if (!post.getAuthorId().equals(command.getMemberId())) {
            throw new IllegalArgumentException(NOT_YOUR_POST.getMessage());
        }

        post.updateContent(command.getContent());
        return post.getRefCode();
    }
}
