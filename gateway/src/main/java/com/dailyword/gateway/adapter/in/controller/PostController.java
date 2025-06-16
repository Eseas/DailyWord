package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.dto.post.CreatePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gateway")
@RequiredArgsConstructor
public class PostController {

    private final PostClient postClient;

    @PostMapping
    public ResponseEntity<APIResponse<Long>> createPost(@RequestBody CreatePostRequest request) {
        Long postId = postClient.createPost(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.success(postId));
    }
}
