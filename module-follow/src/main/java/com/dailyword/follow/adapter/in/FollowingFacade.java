package com.dailyword.follow.adapter.in;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.common.response.APIResponse;
import com.dailyword.follow.adapter.in.dto.GetFollowList;
import com.dailyword.follow.adapter.in.dto.GetFollowingCount;
import com.dailyword.follow.application.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 팔로우 관리 Facade
 * 사용자 간 팔로우/언팔로우 관계 관리 및 팔로워/팔로잉 목록 조회 등의 내부 API를 제공합니다.
 * Gateway 모듈에서 호출하여 사용자 관계 관리 기능을 처리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class FollowingFacade {

    private final GetFollowCountUsecase getFollowCountUsecase;
    private final GetFollowingListUsecase getFollowingListUsecase;
    private final GetFollowerListUsecase getFollowerListUsecase;
    private final FollowUsecase followUsecase;
    private final UnFollowUsecase unFollowUsecase;

    /**
     * 팔로잉 수 조회
     * 특정 사용자가 팔로우하고 있는 사용자의 수를 조회합니다.
     *
     * @param memberId 조회 대상 사용자의 ID
     * @return 팔로잉 수 정보
     */
    @GetMapping("/users/{memberRefCode}/following/count")
    public ResponseEntity<APIResponse<GetFollowingCount>> getFollowingCount(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowCountUsecase.getFollowingCount(memberId)));
    }

    /**
     * 팔로잉 목록 조회
     * 특정 사용자가 팔로우하고 있는 사용자들의 목록을 페이지네이션으로 조회합니다.
     *
     * @param memberId 조회 대상 사용자의 ID
     * @param page 페이지 번호 (0부터 시작)
     * @return 팔로잉 사용자 목록 (페이지네이션 적용)
     */
    @GetMapping("/users/{memberId}/following/list/{page}")
    public ResponseEntity<APIResponse<PageResponse<GetFollowList>>> getFollowingList(
            @PathVariable Long memberId,
            @PathVariable Integer page
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowingListUsecase.getFollowList(memberId, page)));
    }

    /**
     * 팔로워 목록 조회
     * 특정 사용자를 팔로우하고 있는 사용자들의 목록을 페이지네이션으로 조회합니다.
     *
     * @param memberId 조회 대상 사용자의 ID
     * @param page 페이지 번호 (0부터 시작)
     * @return 팔로워 사용자 목록 (페이지네이션 적용)
     */
    @GetMapping("/users/{memberRefCode}/follower/list/{page}")
    public ResponseEntity<APIResponse<PageResponse<GetFollowList>>> getFollowerList(
            @PathVariable Long memberId,
            @PathVariable Integer page
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getFollowerListUsecase.getFollowList(memberId, page)));
    }

    /**
     * 사용자 팔로우
     * 특정 사용자를 팔로우합니다. 이미 팔로우 관계가 존재하는 경우 기존 관계를 활성화합니다.
     *
     * @param memberId 팔로우를 수행할 사용자의 ID
     * @param followeeId 팔로우 대상 사용자의 ID
     * @return 팔로우 완료 응답
     */
    @PostMapping("/users/{memberId}/following/{followeeId}")
    public ResponseEntity<APIResponse> follow(
            @PathVariable Long memberId,
            @PathVariable Long followeeId
    ) {
        followUsecase.follow(memberId, followeeId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }

    /**
     * 사용자 언팔로우
     * 특정 사용자에 대한 팔로우를 해제합니다. 팔로우 관계를 비활성화합니다.
     *
     * @param memberId 언팔로우를 수행할 사용자의 ID
     * @param followeeId 언팔로우 대상 사용자의 ID
     * @return 언팔로우 완료 응답
     */
    @DeleteMapping("/users/{memberId}/following/{followeeId}")
    public ResponseEntity<APIResponse> unfollow(
            @PathVariable Long memberId,
            @PathVariable Long followeeId
    ) {
        unFollowUsecase.unfollow(memberId, followeeId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }
}
