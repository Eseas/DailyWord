package com.dailyword.member.adapter.in.facade;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.common.response.APIResponse;
import com.dailyword.member.adapter.in.facade.dto.*;
import com.dailyword.member.application.usecase.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class PostFacade {

    private final PostPageUsecase postPageUsecase;
    private final UserPostPageUsecase userPostPageUsecase;
    private final PostCreateUsecase postCreateUsecase;
    private final PostReadUsecase postReadUsecase;
    private final PostUpdateUsecase postUpdateUsecase;
    private final PostDeleteUsecase postDeleteUsecase;

    @GetMapping("/posts")
    public ResponseEntity<APIResponse<List<PostPageResponse>>> getPosts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(postPageUsecase.getPosts(page, size)));
    }

    @GetMapping("/users/{memberId}/posts")
    public ResponseEntity<APIResponse<PageResponse<MyPostPageResponse>>> getUserPosts(
        @PathVariable Long memberId,
        @RequestParam int page,
        @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(userPostPageUsecase.getUserPosts(memberId, page, size)));
    }

    @GetMapping("/posts/{postRefCode}")
    public ResponseEntity<APIResponse<PostDetailResponse>> getPost(@PathVariable String postRefCode) {
        PostDetailResponse response = postReadUsecase.getPost(postRefCode);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    @PostMapping("/posts")
    public String createPost(@RequestBody CreatePostRequest request) {
        return postCreateUsecase.createPost(request.toCommand());
    }

    @PutMapping("/posts/{refCode}")
    public ResponseEntity<APIResponse<String>> updatePost(
            @PathVariable String refCode,
            @RequestBody UpdatePostRequest request
    ) {
        String updatedRefCode = postUpdateUsecase.update(request.toCommand(refCode));
        return ResponseEntity.ok(APIResponse.success(updatedRefCode));
    }

    @DeleteMapping("/posts/{refCode}")
    public ResponseEntity<APIResponse<String>> deletePost(
            @PathVariable String refCode,
            @RequestBody DeletePostRequest request
    ) {
        String result = postDeleteUsecase.delete(request.toCommand(refCode));
        return ResponseEntity.ok(APIResponse.success(result));
    }
}
