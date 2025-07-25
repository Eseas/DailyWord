package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.application.usecase.follow.GetFollowCountUsecase;
import com.dailyword.gateway.dto.follow.GetFollowCount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FollowController {

    private final GetFollowCountUsecase getFollowCountUsecase;

    @GetMapping("/users{memberRefCode}/following/count")
    public ResponseEntity<APIResponse<GetFollowCount>> getFollowCount(
            @PathVariable String memberRefCode
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowCountUsecase.getFollowCount(memberRefCode)));
    }
}
