package com.dailyword.gateway.application.service.comments;

import com.dailyword.gateway.adapter.out.client.CommentClient;
import com.dailyword.gateway.application.usecase.comment.CreateCommentUsecase;
import com.dailyword.gateway.dto.comment.CreateCommentRequest;
import com.dailyword.gateway.dto.comment.CreateCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCommentService implements CreateCommentUsecase {

    private final CommentClient commentClient;

    @Override
    public CreateCommentResponse createComment(String refCode, CreateCommentRequest request) {
        return commentClient.createComment(refCode, request).getData();
    }
}
