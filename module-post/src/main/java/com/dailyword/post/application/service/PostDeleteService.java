package com.dailyword.post.application.service;

import com.dailyword.post.application.usecase.PostDeleteUsecase;
import com.dailyword.post.application.usecase.command.DeletePostCommand;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDeleteService implements PostDeleteUsecase {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public String delete(DeletePostCommand command) {
        Post post = postRepository.findByRefCode(command.getRefCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!post.getAuthorId().equals(command.getMemberId())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        post.delete();
        return post.getRefCode();
    }
}

