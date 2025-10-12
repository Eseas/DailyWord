package com.dailyword.post.adapter.in.facade;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.common.response.APIResponse;
import com.dailyword.post.adapter.in.facade.dto.*;
import com.dailyword.post.application.usecase.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 포스트 관련 REST API를 제공하는 외부 인터페이스 퍼사드 클래스
 *
 * 이 클래스는 포스트 CRUD 작업과 관련된 모든 HTTP 요청을 처리하며,
 * 각 유스케이스를 호출하여 비즈니스 로직을 실행합니다.
 * 페이지네이션, 사용자별 포스트 조회, 포스트 숨김 기능 등을 지원합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class PostFacade {

    private final PostPageUsecase postPageUsecase;
    private final UserPostPageUsecase userPostPageUsecase;
    private final PostCreateUsecase postCreateUsecase;
    private final PostReadUsecase postReadUsecase;
    private final PostUpdateUsecase postUpdateUsecase;
    private final PostDeleteUsecase postDeleteUsecase;
    private final PostHideUsecase postHideUsecase;

    /**
     * 전체 포스트 목록을 페이지네이션으로 조회합니다.
     *
     * 활성 상태의 포스트들을 생성일 기준 내림차순으로 정렬하여 반환합니다.
     *
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지당 포스트 수
     * @return 포스트 목록이 포함된 API 응답
     */
    @GetMapping("/posts")
    public ResponseEntity<APIResponse<List<PostPageResponse>>> getPosts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(postPageUsecase.getPosts(page, size)));
    }

    /**
     * 특정 사용자가 작성한 포스트 목록을 조회합니다.
     *
     * 해당 사용자가 작성한 활성 상태의 포스트들을 페이지네이션으로 반환합니다.
     *
     * @param memberId 조회할 사용자의 ID
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지당 포스트 수
     * @return 사용자의 포스트 목록과 페이지 정보가 포함된 API 응답
     */
    @GetMapping("/users/{memberId}/posts")
    public ResponseEntity<APIResponse<PageResponse<MyPostPageResponse>>> getUserPosts(
        @PathVariable Long memberId,
        @RequestParam int page,
        @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(userPostPageUsecase.getUserPosts(memberId, page, size)));
    }

    /**
     * 포스트 참조 코드로 특정 포스트의 상세 정보를 조회합니다.
     *
     * @param postRefCode 조회할 포스트의 참조 코드
     * @return 포스트 상세 정보가 포함된 API 응답
     * @throws IllegalArgumentException 포스트를 찾을 수 없는 경우
     */
    @GetMapping("/posts/{postRefCode}")
    public ResponseEntity<APIResponse<PostDetailResponse>> getPost(
            @PathVariable String postRefCode
    ) {
        PostDetailResponse response = postReadUsecase.getPost(postRefCode);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    /**
     * 새로운 포스트를 생성합니다.
     *
     * 요청받은 정보로 새 포스트를 생성하고 참조 코드를 반환합니다.
     *
     * @param request 포스트 생성 요청 정보 (작성자 ID, 내용, 숨김 여부 포함)
     * @return 생성된 포스트의 참조 코드
     */
    @PostMapping("/posts")
    public String createPost(
            @RequestBody CreatePostRequest request
    ) {
        return postCreateUsecase.createPost(request.toCommand());
    }

    /**
     * 기존 포스트의 내용을 수정합니다.
     *
     * 포스트의 작성자만 수정할 수 있으며, 내용과 숨김 여부를 변경할 수 있습니다.
     *
     * @param refCode 수정할 포스트의 참조 코드
     * @param request 포스트 수정 요청 정보 (새 내용, 숨김 여부, 작성자 ID 포함)
     * @return 수정된 포스트의 참조 코드가 포함된 API 응답
     * @throws IllegalArgumentException 포스트를 찾을 수 없거나 작성자가 아닌 경우
     */
    @PutMapping("/posts/{refCode}")
    public ResponseEntity<APIResponse<String>> updatePost(
            @PathVariable String refCode,
            @RequestBody UpdatePostRequest request
    ) {
        String updatedRefCode = postUpdateUsecase.update(request.toCommand(refCode));
        return ResponseEntity.ok(APIResponse.success(updatedRefCode));
    }

    /**
     * 포스트를 삭제합니다 (소프트 삭제).
     *
     * 포스트의 작성자만 삭제할 수 있으며, 실제로는 상태를 DELETED로 변경합니다.
     *
     * @param refCode 삭제할 포스트의 참조 코드
     * @param request 포스트 삭제 요청 정보 (작성자 ID 포함)
     * @return 삭제된 포스트의 참조 코드가 포함된 API 응답
     * @throws IllegalArgumentException 포스트를 찾을 수 없거나 작성자가 아닌 경우
     */
    @DeleteMapping("/posts/{refCode}")
    public ResponseEntity<APIResponse<String>> deletePost(
            @PathVariable String refCode,
            @RequestBody DeletePostRequest request
    ) {
        String result = postDeleteUsecase.delete(request.toCommand(refCode));
        return ResponseEntity.ok(APIResponse.success(result));
    }

    /**
     * 포스트의 숨김 상태를 토글합니다.
     *
     * 포스트가 숨김 상태라면 공개로, 공개 상태라면 숨김으로 변경합니다.
     *
     * @param postRefCode 숨김 상태를 변경할 포스트의 참조 코드
     * @return 변경된 포스트의 참조 코드가 포함된 API 응답
     * @throws IllegalArgumentException 포스트를 찾을 수 없는 경우
     */
    @PatchMapping("/posts/{postRefCode}/hide")
    public ResponseEntity<APIResponse<String>> hidePost(
            @PathVariable String postRefCode
    ) {
        String result = postHideUsecase.hidePost(postRefCode);
        return ResponseEntity.ok(APIResponse.success(result));
    }
}
