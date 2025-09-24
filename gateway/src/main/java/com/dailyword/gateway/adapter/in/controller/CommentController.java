package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.application.usecase.comment.CreateCommentUsecase;
import com.dailyword.gateway.application.usecase.comment.GetPostCommentUsecase;
import com.dailyword.gateway.dto.comment.CreateCommentRequest;
import com.dailyword.gateway.dto.comment.CreateCommentResponse;
import com.dailyword.gateway.dto.comment.PostCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 관리 Controller
 * 외부 클라이언트의 댓글 관련 요청을 처리하는 Gateway API 컨트롤러입니다.
 * 게시글에 대한 댓글 조회 및 생성 기능을 제공하며, module-comment와 연동하여 댓글 처리를 담당합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway")
public class CommentController {

    private final GetPostCommentUsecase getPostCommentUsecase;
    private final CreateCommentUsecase createCommentUsecase;

    /**
     * 게시글 댓글 목록 조회
     * 특정 게시글에 달린 댓글들을 페이지네이션으로 조회합니다.
     * 댓글은 생성일 순으로 정렬되어 반환됩니다.
     *
     * @param page 페이지 번호 (0부터 시작)
     * @param pageSize 페이지당 댓글 수
     * @param postRefCode 댓글을 조회할 게시글의 참조 코드
     * @return 댓글 목록과 페이지네이션 정보
     */
    @GetMapping("/posts/{postRefCode}/comments")
    public ResponseEntity<APIResponse<PostCommentResponse>> getPostsCommentList(
            @RequestParam Integer page,
            @RequestParam Integer pageSize,
            @PathVariable String postRefCode
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(getPostCommentUsecase.getComments(postRefCode, page, pageSize)));
    }

    /**
     * 댓글 생성
     * 특정 게시글에 새로운 댓글을 작성합니다.
     * 작성자 정보와 댓글 내용을 포함하여 댓글을 생성하고, 생성된 댓글 정보를 반환합니다.
     *
     * @param postRefCode 댓글을 작성할 게시글의 참조 코드
     * @param createCommentRequest 댓글 생성 요청 데이터 (작성자 ID, 댓글 내용 등)
     * @return 생성된 댓글의 정보 (댓글 ID, 작성일시 등)
     */
    @PostMapping("/posts/{postRefCode}/comments")
    public ResponseEntity<APIResponse<CreateCommentResponse>> createComment(
            @PathVariable String postRefCode,
            @RequestBody CreateCommentRequest createCommentRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(createCommentUsecase.createComment(postRefCode, createCommentRequest)));
    }
}
