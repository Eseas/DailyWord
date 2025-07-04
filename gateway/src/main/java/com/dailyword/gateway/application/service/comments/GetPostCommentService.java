package com.dailyword.gateway.application.service.comments;

import com.dailyword.gateway.adapter.out.client.CommentClient;
import com.dailyword.gateway.application.usecase.comment.GetPostCommentUsecase;
import com.dailyword.gateway.dto.comment.PostCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPostCommentService implements GetPostCommentUsecase {

    private final CommentClient commentClient;

    @Override
    public PostCommentResponse getComments(String postRefCode, Integer page, Integer pageSize) {
        return commentClient.getComments(postRefCode, page, pageSize).getData();
    }
}
