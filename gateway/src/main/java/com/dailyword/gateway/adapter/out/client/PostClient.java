package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.application.service.post.command.PostCreateCommand;
import com.dailyword.gateway.dto.mypage.QtProgressResponse;
import com.dailyword.gateway.dto.post.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "postClient", url = "${internal.post.url}")
public interface PostClient {

    @PostMapping("/internal/post")
    APIResponse<String> createPost(@RequestBody PostCreateCommand request);

    @GetMapping("/internal/post")
    APIResponse<List<PostPageResponse>> getPosts(
            @RequestParam int page,
            @RequestParam int size
    );

    @GetMapping("/internal/users/{memberId}/posts")
    APIResponse<List<MyPostPageResponse>> getUserPosts(
            @PathVariable Long memberId,
            @RequestParam int page,
            @RequestParam int size
    );

    @GetMapping("/internal/post/{postRefCode}")
    APIResponse<PostDetailResponse> getPost(@PathVariable String postRefCode);

    @PutMapping("/internal/post/{postRefCode}")
    APIResponse<String> updatePost(@PathVariable String postRefCode, @RequestBody PostUpdateRequest request);

    @DeleteMapping("/internal/post/{refCode}")
    APIResponse<String> deletePost(@PathVariable String refCode, @RequestBody PostDeleteRequest request);

    @GetMapping("/internal/posts/qt-progress/{memberId}")
    APIResponse<QtProgressResponse> getQtProgressDates(@PathVariable Long memberId);
}
