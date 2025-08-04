package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.follow.GetFollowCount;
import com.dailyword.gateway.dto.follow.GetFollowingList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "followClient", url = "${internal.follow.url}")
public interface FollowClient {

    @GetMapping("/internal/users/{memberId}/following/count")
    APIResponse<GetFollowCount> getFollowCount(@PathVariable("memberId") Long memberId);

    @GetMapping("/users/{memberId}/following/list/{page}")
    APIResponse<PageResponse<GetFollowingList>> getFollowingList(
            @PathVariable("memberId") Long memberId,
            @PathVariable("page") Integer page
    );

    @PostMapping("/internal/users/{memberId}/following/{followeeId}")
    APIResponse follow(Long memberId, Long followeeId);

    @DeleteMapping("/internal/users/{memberId}/following/{followeeId}")
    APIResponse unFollow(Long memberId, Long followeeId);
}
