package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.application.usecase.post.*;
import com.dailyword.gateway.dto.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시글 관리 Controller
 * 외부 클라이언트의 게시글 관련 요청을 처리하는 Gateway API 컨트롤러입니다.
 * 게시글 CRUD(생성, 조회, 수정, 삭제), 페이지네이션 조회, 숨기기 기능을 제공하며,
 * module-post와 연동하여 게시글 처리를 담당합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/gateway")
@RequiredArgsConstructor
public class PostController {

    private final PostCreateUsecase postCreateUsecase;
    private final GetUserPostUsecase getUserPostUsecase;
    private final PostPageUsecase postPageUsecase;
    private final PostReadUsecase postReadUsecase;
    private final PostUpdateUsecase postUpdateUsecase;
    private final PostDeleteUsecase postDeleteUsecase;
    private final PostHideUsecase postHideUsecase;

    /**
     * 게시글 목록 조회
     * 전체 게시글을 페이지네이션으로 조회합니다.
     * 게시글은 최신 순으로 정렬되어 반환되며, 숨김 및 비공개 게시글은 제외됩니다.
     *
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지당 게시글 수 (기본값: 10)
     * @return 게시글 목록과 기본 정보
     */
    @GetMapping("/posts")
    public ResponseEntity<APIResponse<List<PostPageResponse>>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostPageResponse> postList = postPageUsecase.getPosts(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(postList));
    }

    /**
     * 사용자 게시글 목록 조회
     * 특정 사용자가 작성한 게시글들을 페이지네이션으로 조회합니다.
     * 마이페이지에서 내 게시글 목록을 볼 때 사용됩니다.
     *
     * @param userRefCode 게시글을 조회할 사용자의 참조 코드
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지당 게시글 수 (기본값: 10)
     * @return 사용자의 게시글 목록과 페이지네이션 정보
     */
    @GetMapping("/users/{userRefCode}/posts")
    public ResponseEntity<APIResponse<PageResponse<MyPostPageResponse>>> getUserPosts(
            @PathVariable String userRefCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getUserPostUsecase.getMyPostList(userRefCode, page, size)));
    }

    /**
     * 게시글 상세 조회
     * 특정 게시글의 상세 정보를 조회합니다.
     * 게시글 내용, 작성자 정보, 자'성일시, 첫 번째 이미지 등을 포함합니다.
     *
     * @param postRefCode 조회할 게시글의 참조 코드
     * @return 게시글의 상세 정보
     */
    @GetMapping("/posts/{postRefCode}")
    public ResponseEntity<APIResponse<PostDetailResponse>> getPost(@PathVariable String postRefCode) {
        PostDetailResponse response = postReadUsecase.getPost(postRefCode);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    /**
     * 게시글 생성
     * 새로운 게시글을 작성합니다.
     * 제목, 내용, 이미지, 공개 여부 등의 정보를 포함하여 게시글을 생성합니다.
     *
     * @param request 게시글 생성 요청 데이터 (제목, 내용, 작성자 ID 등)
     * @return 생성된 게시글의 참조 코드
     */
    @PostMapping("/posts")
    public ResponseEntity<APIResponse<String>> createPost(@RequestBody CreatePostRequest request) {
        String postRefCode = postCreateUsecase.createPost(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.success(postRefCode));
    }

    /**
     * 게시글 수정
     * 기존 게시글의 내용을 수정합니다.
     * 제목, 내용, 이미지, 공개 여부 등을 변경할 수 있으며, 작성자만 수정 가능합니다.
     *
     * @param refCode 수정할 게시글의 참조 코드
     * @param request 게시글 수정 요청 데이터
     * @return 수정 결과 메시지
     */
    @PutMapping("/posts/{refCode}")
    public ResponseEntity<APIResponse<String>> updatePost(
            @PathVariable String refCode,
            @RequestBody PostUpdateRequest request
    ) {
        String result = postUpdateUsecase.update(refCode, request);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(result));
    }

    /**
     * 게시글 삭제
     * 기존 게시글을 삭제합니다.
     * 작성자만 삭제할 수 있으며, 삭제 시 관련된 댑글과 파일도 함께 처리됩니다.
     *
     * @param refCode 삭제할 게시글의 참조 코드
     * @param request 게시글 삭제 요청 데이터 (삭제 권한 확인용)
     * @return 삭제 결과 메시지
     */
    @DeleteMapping("/posts/{refCode}")
    public ResponseEntity<APIResponse<String>> deletePost(
            @PathVariable String refCode,
            @RequestBody PostDeleteRequest request
    ) {
        String result = postDeleteUsecase.delete(refCode, request);
        return ResponseEntity.ok(APIResponse.success(result));
    }

    /**
     * 게시글 숨기기
     * 게시글을 숨김 상태로 변경합니다.
     * 숨겨진 게시글은 일반 사용자에게 보이지 않으며, 관리자만 확인할 수 있습니다.
     *
     * @param postRefCode 숨길 게시글의 참조 코드
     * @return 숨기기 결과 메시지
     */
    @PatchMapping("/posts/{postRefCode}/hide")
    public ResponseEntity<APIResponse<String>> hidePost(@PathVariable String postRefCode) {
        String result = postHideUsecase.hidePost(postRefCode);
        return ResponseEntity.ok(APIResponse.success(result));
    }
}
