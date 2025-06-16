package com.dailyword.post.facade;

import com.dailyword.post.application.usecase.PostCreateUsecase;
import com.dailyword.post.facade.dto.PostCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/post")
@RequiredArgsConstructor
public class PostFacade {

    private final PostCreateUsecase postCreateUsecase;

    @PostMapping
    public Long createPost(@RequestBody PostCreateRequest request) {
        return postCreateUsecase.createPost(request.toCommand());
    }
}
