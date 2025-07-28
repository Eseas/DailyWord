package com.dailyword.follow.adapter.in;

import com.dailyword.common.response.APIResponse;
import com.dailyword.follow.adapter.in.dto.GetFollowingCount;
import com.dailyword.follow.application.usecase.FollowUsecase;
import com.dailyword.follow.application.usecase.GetFollowCountUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class FollowingFacade {

    private final GetFollowCountUsecase getFollowCountUsecase;
    private final FollowUsecase followUsecase;

    @GetMapping("/users/{memberRefCode}/following/count")
    public ResponseEntity<APIResponse<GetFollowingCount>> getFollowingCount(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowCountUsecase.getFollowingCount(memberId)));
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
}
