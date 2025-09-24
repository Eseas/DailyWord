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

/**
 * 마이페이지 관리 Controller
 * 외부 클라이언트의 마이페이지 관련 요청을 처리하는 Gateway API 컨트롤러입니다.
 * 회원의 마이페이지 메인 정보 조회 기능을 제공하며, 여러 모듈과 연동하여 사용자의 통합 정보를 제공합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MypageController {

    private final GetMypageMainInfoUsecase getMypageMainInfoUsecase;

    /**
     * 마이페이지 메인 정보 조회
     * 회원의 마이페이지에 표시될 주요 정보들을 조회합니다.
     * 회원 기본 정보, 작성한 게시글 수, 팔로워/팔로잉 수 등의 통계 정보를 포함합니다.
     *
     * @param refCode 마이페이지 정보를 조회할 회원의 참조 코드
     * @return 마이페이지 메인 화면에 필요한 회원 정보 및 통계 데이터
     */
    @GetMapping("/mypage/{refCode}")
    public ResponseEntity<APIResponse<MypageMainResponse>> getMypageInfo(
            @PathVariable String refCode
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getMypageMainInfoUsecase.getMypageMainInfo(refCode)));
    }

}
