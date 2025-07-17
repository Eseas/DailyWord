package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.application.usecase.mypage.GetMypageMainInfoUsecase;
import com.dailyword.gateway.dto.mypage.MypageMainResponse;
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
public class MypageController {

    private final GetMypageMainInfoUsecase getMypageMainInfoUsecase;

    @GetMapping("/mypage/{refCode}")
    public ResponseEntity<APIResponse<MypageMainResponse>> getMypageInfo(
            @PathVariable String refCode
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getMypageMainInfoUsecase.getMypageMainInfo(refCode)));
    }

}
