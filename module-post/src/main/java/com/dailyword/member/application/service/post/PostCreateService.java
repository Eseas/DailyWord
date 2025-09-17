package com.dailyword.member.application.service.post;

import com.dailyword.member.application.usecase.post.PostCreateUsecase;
import com.dailyword.member.application.usecase.command.CreatePostCommand;
import com.dailyword.member.domain.model.Post;
import com.dailyword.member.repository.PostRepository;
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

        Post post = Post.create(command.getAuthorId(), command.getContent(), command.getIsHide());

        return postRepository.save(post).getRefCode();
    }
}
