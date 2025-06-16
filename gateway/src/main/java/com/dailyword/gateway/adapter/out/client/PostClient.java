package com.dailyword.gateway.adapter.out.client;

import com.dailyword.gateway.dto.post.CreatePostRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "module-post", url = "${internal.post.url}")
public interface PostClient {

    @PostMapping("/internal/post")
    Long createPost(@RequestBody CreatePostRequest request);
}
