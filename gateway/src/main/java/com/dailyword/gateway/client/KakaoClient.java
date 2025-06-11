package com.dailyword.gateway.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.kakao.KakaoUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoClient", url = "${module.kakao.url}")
public interface KakaoClient {

    @PostMapping("/internal/kakao/login")
    APIResponse<KakaoUserInfoResponse> getUserInfo(@RequestParam("code") String kakaoCode);
}
