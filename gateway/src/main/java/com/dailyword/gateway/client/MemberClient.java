package com.dailyword.gateway.client;

import com.dailyword.gateway.dto.member.PatchMemberInfo;
import com.dailyword.gateway.dto.member.PatchPassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.kakao.KakaoUserInfoResponse;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "memberClient", url = "${module.member.url}")
public interface MemberClient {

    @GetMapping("/internal/member/{id}")
    APIResponse<GetMemberInfo.Response> getMemberInfo(@PathVariable("id") Long memberId);

    @PostMapping("/internal/members/login")
    APIResponse<GetMemberInfo.Response> login(@RequestBody KakaoUserInfoResponse kakaoUserInfo);

    @PostMapping("/internal/members")
    APIResponse<GetMemberInfo.Response> register(@RequestBody KakaoUserInfoResponse kakaoUserInfo);

    @PatchMapping("/internal/member/{memberId}/password")
    void patchPassword(@PathVariable("memberId") Long memberId,
                       @RequestBody PatchPassword.Request requestDto);

    @PatchMapping("/internal/members/{memberId}/info")
    void patchMemberInfo(@PathVariable("memberId") Long memberId,
                         @RequestBody PatchMemberInfo.Request requestDto);
}
