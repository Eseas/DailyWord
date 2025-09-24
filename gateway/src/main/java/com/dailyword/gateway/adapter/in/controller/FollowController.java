package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.application.usecase.follow.FollowUsecase;
import com.dailyword.gateway.application.usecase.follow.GetFollowCountUsecase;
import com.dailyword.gateway.application.usecase.follow.GetFollowingListUsecase;
import com.dailyword.gateway.application.usecase.follow.UnFollowUsecase;
import com.dailyword.gateway.dto.follow.GetFollowCount;
import com.dailyword.gateway.dto.follow.GetFollowingList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 팔로우 관리 Controller
 * 외부 클라이언트의 팔로우 관련 요청을 처리하는 Gateway API 컨트롤러입니다.
 * 팔로우/언팔로우, 팔로잉/팔로워 목록 조회, 팔로우 수 조회 기능을 제공하며,
 * module-member와 연동하여 팔로우 처리를 담당합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FollowController {

    private final GetFollowCountUsecase getFollowCountUsecase;
    private final GetFollowingListUsecase getFollowingListUsecase;
//    private final GetFollowerListUsecase getFollowerListUsecase;
    private final FollowUsecase followUsecase;
    private final UnFollowUsecase unFollowUsecase;

    /**
     * 팔로우 수 조회
     * 특정 회원의 팔로워 수와 팔로잉 수를 조회합니다.
     * 마이페이지나 프로필 화면에서 표시될 팔로우 통계 정보를 제공합니다.
     *
     * @param memberRefCode 팔로우 수를 조회할 회원의 참조 코드
     * @return 팔로워 수와 팔로잉 수 정보
     */
    @GetMapping("/users/{memberRefCode}/following/count")
    public ResponseEntity<APIResponse<GetFollowCount>> getFollowCount(
            @PathVariable String memberRefCode
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowCountUsecase.getFollowCount(memberRefCode)));
    }

    /**
     * 팔로잉 목록 조회
     * 특정 회원이 팔로우하고 있는 다른 회원들의 목록을 페이지네이션으로 조회합니다.
     * 팔로잉한 회원들의 기본 정보(닉네임, 프로필 이미지 등)를 제공합니다.
     *
     * @param memberRefCode 팔로잉 목록을 조회할 회원의 참조 코드
     * @param page 페이지 번호 (0부터 시작)
     * @return 팔로잉 목록과 페이지네이션 정보
     */
    @GetMapping("/users/{memberRefCode}/following/list/{page}")
    public ResponseEntity<APIResponse<PageResponse<GetFollowingList>>> getFollowingList(
            @PathVariable String memberRefCode,
            @PathVariable Integer page
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowingListUsecase.getFollowList(memberRefCode, page)));
    }

    /**
     * 팔로워 목록 조회
     * 특정 회원을 팔로우하고 있는 다른 회원들의 목록을 페이지네이션으로 조회합니다.
     * 현재 구현 중이라 null을 반환합니다.
     *
     * @param memberRefCode 팔로워 목록을 조회할 회원의 참조 코드
     * @param page 페이지 번호 (0부터 시작)
     * @return 현재 null (추후 팔로워 목록과 페이지네이션 정보 반환 예정)
     */
    @GetMapping("/users/{memberRefCode}/follower/list/{page}")
    public ResponseEntity<APIResponse<PageResponse<GetFollowingList>>> getFollowerList(
            @PathVariable String memberRefCode,
            @PathVariable Integer page
    ) {
        return null;
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(APIResponse.success(getFollowerListUsecase.getFollowerList(memberRefCode, page)));
    }

    /**
     * 팔로우 실행
     * 특정 회원을 팔로우합니다.
     * 이미 팔로우 중인 경우 에러를 발생시키며, 자기 자신을 팔로우할 수 없습니다.
     *
     * @param memberRefCode 팔로우를 실행할 회원의 참조 코드
     * @param followeeRefCode 팔로우될 회원의 참조 코드
     * @return 팔로우 완료 응답
     */
    @PostMapping("/users/{memberRefCode}/following/{followeeRefCode}")
    public ResponseEntity<APIResponse> follow(
            @PathVariable String memberRefCode,
            @PathVariable String followeeRefCode
    ) {
        followUsecase.follow(memberRefCode, followeeRefCode);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }

    /**
     * 언팔로우 실행
     * 특정 회원에 대한 팔로우를 취소합니다.
     * 팔로우 상태가 아닌 경우 에러를 발생시킵니다.
     *
     * @param memberRefCode 언팔로우를 실행할 회원의 참조 코드
     * @param followeeRefCode 언팔로우될 회원의 참조 코드
     * @return 언팔로우 완료 응답
     */
    @DeleteMapping("/users/{memberRefCode}/following/{followeeRefCode}")
    public ResponseEntity<APIResponse> unfollow(
            @PathVariable String memberRefCode,
            @PathVariable String followeeRefCode
    ) {
        unFollowUsecase.unFollow(memberRefCode, followeeRefCode);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }
}
