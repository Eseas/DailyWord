package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.comment.PostCommentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "commentClient", url = "${internal.post.url}")
public interface CommentClient {

    @GetMapping("/internal/posts/{postRefCode}/comments")
    APIResponse<PostCommentResponse> getComments(
            @PathVariable("postRefCode") String postRefCode,
            @RequestParam Integer page,
            @RequestParam Integer pageSize
    );
}
