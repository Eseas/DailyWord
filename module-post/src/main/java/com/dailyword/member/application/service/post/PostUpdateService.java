package com.dailyword.member.application.service.post;

import com.dailyword.member.application.usecase.post.PostUpdateUsecase;
import com.dailyword.member.application.usecase.command.UpdatePostCommand;
import com.dailyword.member.domain.model.Post;
import com.dailyword.member.domain.model.PostStatus;
import com.dailyword.member.repository.PostRepository;
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
        Post post = postRepository.findByRefCodeAndStatus(command.getRefCode(), PostStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));

        if (!post.getAuthorId().equals(command.getMemberId())) {
            throw new IllegalArgumentException(NOT_YOUR_POST.getMessage());
        }

        post.updateContent(command.getContent());
        post.updateIsHide(command.getIsHide());
        return post.getRefCode();
    }
}
