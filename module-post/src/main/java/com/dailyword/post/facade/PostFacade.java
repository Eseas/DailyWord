package com.dailyword.post.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.post.application.usecase.post.*;
import com.dailyword.post.facade.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/post")
@RequiredArgsConstructor
public class PostFacade {

    private final PostPageUsecase postPageUsecase;
    private final PostCreateUsecase postCreateUsecase;
    private final PostReadUsecase postReadUsecase;
    private final PostUpdateUsecase postUpdateUsecase;
    private final PostDeleteUsecase postDeleteUsecase;

    @GetMapping
    public ResponseEntity<APIResponse<List<PostPageResponse>>> getPosts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(postPageUsecase.getPosts(page, size)));
    }

    @GetMapping("/{postRefCode}")
    public ResponseEntity<APIResponse<PostDetailResponse>> getPost(@PathVariable String postRefCode) {
        PostDetailResponse response = postReadUsecase.getPost(postRefCode);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    @PostMapping
    public String createPost(@RequestBody PostCreateRequest request) {
        return postCreateUsecase.createPost(request.toCommand());
    }

    @PutMapping("/{refCode}")
    public ResponseEntity<APIResponse<String>> updatePost(
            @PathVariable String refCode,
            @RequestBody PostUpdateRequest request
    ) {
        String updatedRefCode = postUpdateUsecase.update(request.toCommand(refCode));
        return ResponseEntity.ok(APIResponse.success(updatedRefCode));
    }

    @DeleteMapping("/{refCode}")
    public ResponseEntity<APIResponse<String>> deletePost(
            @PathVariable String refCode,
            @RequestBody PostDeleteRequest request
    ) {
        String result = postDeleteUsecase.delete(request.toCommand(refCode));
        return ResponseEntity.ok(APIResponse.success(result));
    }
}
