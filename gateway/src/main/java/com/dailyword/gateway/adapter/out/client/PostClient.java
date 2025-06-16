package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.post.CreatePostRequest;
import com.dailyword.gateway.dto.post.PostPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "module-post", url = "${internal.post.url}")
public interface PostClient {

    @PostMapping("/internal/post")
    Long createPost(@RequestBody CreatePostRequest request);

    @GetMapping("/internal/post")
    APIResponse<List<PostPageResponse>> getPosts(
            @RequestParam int page,
            @RequestParam int size
    );
}
