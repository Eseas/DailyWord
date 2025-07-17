package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.mypage.MypageMainResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mypageClient", url = "${module.mypage.url}")
public interface MypageClient {

    @GetMapping("/internal/mypage/{memberId}")
    APIResponse<MypageMainResponse> getMypageMainInfo(@PathVariable("memberId") Long memberId);
}
