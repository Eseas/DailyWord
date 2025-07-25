package com.dailyword.follow.adapter.in;

import com.dailyword.common.response.APIResponse;
import com.dailyword.follow.adapter.in.dto.GetFollowingCount;
import com.dailyword.follow.application.usecase.GetFollowCountUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class FollowingFacade {

    private final GetFollowCountUsecase getFollowCountUsecase;

    @GetMapping("/users{memberRefCode}/following/count")
    public ResponseEntity<APIResponse<GetFollowingCount>> getFollowingCount(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowCountUsecase.getFollowingCount(memberId)));
    }
}
