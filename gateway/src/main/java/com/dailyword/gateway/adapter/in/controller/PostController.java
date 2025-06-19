package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.application.usecase.post.*;
import com.dailyword.gateway.dto.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gateway")
@RequiredArgsConstructor
public class PostController {

    private final PostCreateUsecase postCreateUsecase;
    private final PostPageUsecase postPageUsecase;
    private final PostReadUsecase postReadUsecase;
    private final PostUpdateUsecase postUpdateUsecase;
    private final PostDeleteUsecase postDeleteUsecase;

    @GetMapping
    public ResponseEntity<APIResponse<List<PostPageResponse>>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostPageResponse> postList = postPageUsecase.getPosts(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(postList));
    }

    @GetMapping("/{postRefCode}")
    public ResponseEntity<APIResponse<PostDetailResponse>> getPost(@PathVariable String postRefCode) {
        PostDetailResponse response = postReadUsecase.getPost(postRefCode);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<APIResponse<Long>> createPost(@RequestBody CreatePostRequest request) {
        Long postId = postCreateUsecase.createPost(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.success(postId));
    }

    @PutMapping("/{refCode}")
    public ResponseEntity<APIResponse<String>> updatePost(
            @PathVariable String refCode,
            @RequestBody PostUpdateRequest request
    ) {
        String result = postUpdateUsecase.update(refCode, request);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(result));
    }

    @DeleteMapping("/{refCode}")
    public ResponseEntity<APIResponse<String>> deletePost(
            @PathVariable String refCode,
            @RequestBody PostDeleteRequest request
    ) {
        String result = postDeleteUsecase.delete(refCode, request);
        return ResponseEntity.ok(APIResponse.success(result));
    }
}
