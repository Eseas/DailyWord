package com.dailyword.gateway.adapter.out.client;

import com.dailyword.gateway.dto.member.PatchMemberInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.kakao.KakaoUserInfoResponse;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "memberClient", url = "${module.member.url}")
public interface MemberClient {

    @GetMapping("/internal/members/id-by-ref-code/{refCode}")
    APIResponse<Long> idByRefCode(@PathVariable("refCode") String refCode);
    //TODO - change GetMemberInfo multi use
    @GetMapping("/internal/member/{id}")
    APIResponse<GetMemberInfo> getMemberInfo(@PathVariable("id") Long memberId);

    @PostMapping("/internal/members/login")
    APIResponse<GetMemberInfo> login(@RequestBody KakaoUserInfoResponse kakaoUserInfo);

    @PostMapping("/internal/members")
    APIResponse<GetMemberInfo> register(@RequestBody KakaoUserInfoResponse kakaoUserInfo);

    @PatchMapping("/internal/members/{memberId}/info")
    void patchMemberInfo(@PathVariable("memberId") Long memberId,
                         @RequestBody PatchMemberInfo.Request requestDto);
}
