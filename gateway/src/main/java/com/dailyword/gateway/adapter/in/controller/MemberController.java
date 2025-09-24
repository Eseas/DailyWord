package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.member.EditMemberInfoUsecase;
import com.dailyword.gateway.application.usecase.member.GetMemberInfoUsecase;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import com.dailyword.gateway.dto.member.PatchMemberInfo;
import com.dailyword.gateway.application.service.member.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * 회원 관리 Controller
 * 외부 클라이언트의 회원 관련 요청을 처리하는 Gateway API 컨트롤러입니다.
 * 회원 정보 조회, 소셜 로그인, 회원 정보 수정 기능을 제공하며, module-member와 연동하여 회원 처리를 담당합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway")
public class MemberController {

    private final GetMemberInfoUsecase getMemberInfoUsecase;
    private final SocialLoginService socialLoginUsecase;
    private final EditMemberInfoUsecase editMemberInfoUsecase;

    /**
     * 회원 정보 조회
     * 회원 참조 코드를 통해 특정 회원의 상세 정보를 조회합니다.
     * 공개 프로필 정보(닉네임, 프로필 이미지 등)를 반환합니다.
     *
     * @param memberRefCode 조회할 회원의 참조 코드
     * @return 회원의 공개 프로필 정보
     */
    @GetMapping("/member/{memberRefCode}")
    public ResponseEntity<APIResponse<GetMemberInfo>> getMember(
            @PathVariable String memberRefCode
    ) {
        var memberInfo = getMemberInfoUsecase.getMemberInfo(memberRefCode);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(memberInfo));
    }

    /**
     * 소셜 로그인
     * 소셜 로그인 플랫폼에서 받은 인증 코드를 통해 로그인을 처리합니다.
     * 현재는 카카오 로그인만 지원하며, 로그인 성공 시 JWT 토큰을 발급합니다.
     * Refresh Token은 보안을 위해 HttpOnly 쿠키로 설정됩니다.
     *
     * @param type 소셜 로그인 타입 (현재 "kakao"만 지원)
     * @param code 소셜 로그인 플랫폼에서 받은 인증 코드
     * @return Access Token (Authorization 헤더)과 Refresh Token (HttpOnly 쿠키)
     */
    @PostMapping("/auth/members/login/{type}")
    public ResponseEntity<APIResponse> postLogin(
            @PathVariable String type,
            @RequestParam String code
    ) {
        TokenResponse token = socialLoginUsecase.kakaoLogin(code);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", token.getAccessToken())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(APIResponse.success());
    }

    /**
     * 회원 정보 수정
     * 회원의 프로필 정보를 수정합니다.
     * 닉네임, 프로필 이미지, 소개글 등의 정보를 변경할 수 있습니다.
     *
     * @param memberRefCode 정보를 수정할 회원의 참조 코드
     * @param requestDto 수정할 회원 정보 데이터
     * @return 수정 완료 응답
     */
    @PatchMapping("/members/{memberRefCode}/info")
    public ResponseEntity<APIResponse<Long>> patchMemberInfo(
            @PathVariable String memberRefCode,
            @RequestBody PatchMemberInfo.Request requestDto
    ) {
        editMemberInfoUsecase.editInfo(memberRefCode, requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }
}
