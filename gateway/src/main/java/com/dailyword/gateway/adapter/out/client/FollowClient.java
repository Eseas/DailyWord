package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.follow.GetFollowCount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "followClient", url = "${internal.follow.url}")
public interface FollowClient {

    @GetMapping("/internal/users/{memberId}/following/count")
    APIResponse<GetFollowCount> getFollowCount(@PathVariable("memberId") Long memberId);

    @PostMapping("/internal/users/{memberId}/following/{followeeId}")
    APIResponse follow(Long memberId, Long followeeId);
}
