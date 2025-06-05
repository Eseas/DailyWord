package com.dailyword.auth.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.auth.dto.KakaoUserInfoResponse;
import com.dailyword.auth.service.KakaoAuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/internal")
public class KakaoFacade {

    private final KakaoAuthServiceImpl authService;

    @PostMapping("/kakao/login")
    public ResponseEntity<APIResponse<KakaoUserInfoResponse>> kakaoLogin(@RequestParam("username") String username) {
        String accessToken = authService.getAccessToken(username);
        KakaoUserInfoResponse userInfo = authService.getUserInfo(accessToken);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(userInfo));
    }
}
