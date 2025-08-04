package com.dailyword.follow.adapter.in;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.common.response.APIResponse;
import com.dailyword.follow.adapter.in.dto.GetFollowList;
import com.dailyword.follow.adapter.in.dto.GetFollowingCount;
import com.dailyword.follow.application.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class FollowingFacade {

    private final GetFollowCountUsecase getFollowCountUsecase;
    private final GetFollowingListUsecase getFollowingListUsecase;
    private final GetFollowerListUsecase getFollowerListUsecase;
    private final FollowUsecase followUsecase;
    private final UnFollowUsecase unFollowUsecase;

    @GetMapping("/users/{memberRefCode}/following/count")
    public ResponseEntity<APIResponse<GetFollowingCount>> getFollowingCount(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowCountUsecase.getFollowingCount(memberId)));
    }

    @GetMapping("/users/{memberId}/following/list/{page}")
    public ResponseEntity<APIResponse<PageResponse<GetFollowList>>> getFollowingList(
            @PathVariable Long memberId,
            @PathVariable Integer page
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowingListUsecase.getFollowList(memberId, page)));
    }

    @GetMapping("/users/{memberRefCode}/follower/list/{page}")
    public ResponseEntity<APIResponse<PageResponse<GetFollowList>>> getFollowerList(
            @PathVariable Long memberId,
            @PathVariable Integer page
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowerListUsecase.getFollowList(memberId, page)));
    }

    @PostMapping("/users/{memberId}/following/{followeeId}")
    public ResponseEntity<APIResponse> follow(
            @PathVariable Long memberId,
            @PathVariable Long followeeId
    ) {
        followUsecase.follow(memberId, followeeId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }

    @DeleteMapping("/users/{memberId}/following/{followeeId}")
    public ResponseEntity<APIResponse> unfollow(
            @PathVariable Long memberId,
            @PathVariable Long followeeId
    ) {
        unFollowUsecase.unfollow(memberId, followeeId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }
}
