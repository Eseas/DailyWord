package com.dailyword.post.application.service;

import com.dailyword.post.application.usecase.PostCreateUsecase;
import com.dailyword.post.application.usecase.command.CreatePostCommand;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCreateService implements PostCreateUsecase {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public String createPost(CreatePostCommand command) {

        Post post = Post.create(command.getAuthorId(), command.getContent());

        return postRepository.save(post).getRefCode();
    }
}
