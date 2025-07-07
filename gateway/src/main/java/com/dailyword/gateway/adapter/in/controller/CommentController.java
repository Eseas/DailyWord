package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.application.usecase.comment.CreateCommentUsecase;
import com.dailyword.gateway.application.usecase.comment.GetPostCommentUsecase;
import com.dailyword.gateway.dto.comment.CreateCommentRequest;
import com.dailyword.gateway.dto.comment.CreateCommentResponse;
import com.dailyword.gateway.dto.comment.PostCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway")
public class CommentController {

    private final GetPostCommentUsecase getPostCommentUsecase;
    private final CreateCommentUsecase createCommentUsecase;

    @GetMapping("/posts/{postRefCode}/comments")
    public ResponseEntity<APIResponse<PostCommentResponse>> getPostsCommentList(
            @RequestParam Integer page,
            @RequestParam Integer pageSize,
            @PathVariable String postRefCode
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getPostCommentUsecase.getComments(postRefCode, page, pageSize)));
    }

    @PostMapping("/posts/{postRefCode}/comments")
    public ResponseEntity<APIResponse<CreateCommentResponse>> createComment(
            @PathVariable String postRefCode,
            @RequestBody CreateCommentRequest createCommentRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(createCommentUsecase.createComment(postRefCode, createCommentRequest)));
    }
}
