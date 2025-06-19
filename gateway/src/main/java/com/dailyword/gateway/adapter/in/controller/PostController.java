package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.PostUpdateUsecase;
import com.dailyword.gateway.application.usecase.post.PostCreateUsecase;
import com.dailyword.gateway.application.usecase.post.PostPageUsecase;
import com.dailyword.gateway.application.usecase.post.PostReadUsecase;
import com.dailyword.gateway.dto.post.CreatePostRequest;
import com.dailyword.gateway.dto.post.PostDetailResponse;
import com.dailyword.gateway.dto.post.PostPageResponse;
import com.dailyword.gateway.dto.post.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gateway")
@RequiredArgsConstructor
public class PostController {

    private final PostCreateUsecase postCreateUsecase;
    private final PostPageUsecase postPageUsecase;
    private final PostReadUsecase postReadUsecase;
    private final PostUpdateUsecase postUpdateUsecase;

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
}
