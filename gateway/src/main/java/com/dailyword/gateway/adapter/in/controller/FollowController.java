package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.application.usecase.follow.FollowUsecase;
import com.dailyword.gateway.application.usecase.follow.GetFollowCountUsecase;
import com.dailyword.gateway.application.usecase.follow.GetFollowingListUsecase;
import com.dailyword.gateway.application.usecase.follow.UnFollowUsecase;
import com.dailyword.gateway.dto.follow.GetFollowCount;
import com.dailyword.gateway.dto.follow.GetFollowingList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FollowController {

    private final GetFollowCountUsecase getFollowCountUsecase;
    private final GetFollowingListUsecase getFollowingListUsecase;
    private final FollowUsecase followUsecase;
    private final UnFollowUsecase unFollowUsecase;

    @GetMapping("/users/{memberRefCode}/following/count")
    public ResponseEntity<APIResponse<GetFollowCount>> getFollowCount(
            @PathVariable String memberRefCode
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowCountUsecase.getFollowCount(memberRefCode)));
    }

    @GetMapping("/users/{memberRefCode}/following/list/{page}")
    public ResponseEntity<APIResponse<PageResponse<GetFollowingList>>> getFollowingList(
            @PathVariable String memberRefCode,
            @PathVariable Integer page
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowingListUsecase.getFollowList(memberRefCode, page)));
    }

    @PostMapping("/users/{memberRefCode}/following/{followeeRefCode}")
    public ResponseEntity<APIResponse> follow(
            @PathVariable String memberRefCode,
            @PathVariable String followeeRefCode
    ) {
        followUsecase.follow(memberRefCode, followeeRefCode);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }

    @DeleteMapping("/users/{memberRefCode}/following/{followeeRefCode}")
    public ResponseEntity<APIResponse> unfollow(
            @PathVariable String memberRefCode,
            @PathVariable String followeeRefCode
    ) {
        unFollowUsecase.unFollow(memberRefCode, followeeRefCode);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }
}
