package com.dailyword.auth.adapter.in.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.auth.dto.KakaoUserInfoResponse;
import com.dailyword.auth.application.service.KakaoAuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 카카오 인증 Facade
 * 카카오 소셜 로그인 관련 내부 API를 제공합니다.
 * Gateway 모듈에서 호출하여 카카오 OAuth2 인증 및 사용자 정보 조회 기능을 처리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/internal")
public class KakaoFacade {

    private final KakaoAuthServiceImpl authService;

    /**
     * 카카오 소셜 로그인
     * 카카오 인증코드를 통해 액세스 토큰을 발급받고 사용자 정보를 조회합니다.
     * 카카오 API를 호출하여 사용자의 프로필 정보를 가져와 반환합니다.
     *
     * @param username 카카오 로그인에 사용할 사용자명 또는 인증코드
     * @return 카카오 사용자 정보 (닉네임, 이메일, 프로필 이미지 등)
     */
    @PostMapping("/kakao/login")
    public ResponseEntity<APIResponse<KakaoUserInfoResponse>> kakaoLogin(@RequestParam("username") String username) {
        String accessToken = authService.getAccessToken(username);
        KakaoUserInfoResponse userInfo = authService.getUserInfo(accessToken);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(userInfo));
    }
}
