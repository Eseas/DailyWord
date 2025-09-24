package com.dailyword.post.adapter.in.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.post.application.usecase.command.DeleteCommentCommand;
import com.dailyword.post.application.usecase.comment.CreateCommentUsecase;
import com.dailyword.post.application.usecase.comment.DeleteCommentUsecase;
import com.dailyword.post.application.usecase.comment.GetPostCommentsUsecase;
import com.dailyword.post.adapter.in.facade.dto.CreateCommentRequest;
import com.dailyword.post.adapter.in.facade.dto.CreateCommentResponse;
import com.dailyword.post.adapter.in.facade.dto.PostCommentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 관련 REST API를 제공하는 외부 인터페이스 퍼사드 클래스
 *
 * 이 클래스는 포스트의 댓글 CRUD 작업과 관련된 모든 HTTP 요청을 처리하며,
 * 댓글 조회, 생성, 삭제 기능을 제공합니다.
 * 댓글 수정 기능은 현재 지원하지 않으며, 각 유스케이스를 호출하여 비즈니스 로직을 실행합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class CommentFacade {

    private final GetPostCommentsUsecase getPostCommentsUsecase;
    private final CreateCommentUsecase createCommentUsecase;
    private final DeleteCommentUsecase deleteCommentUsecase;

    /**
     * 특정 포스트의 댓글 목록을 페이지네이션으로 조회합니다.
     *
     * 활성 상태의 댓글들을 조회하며, 비즈니스 로직에 따라 정렬됩니다.
     *
     * @param refCode 댓글을 조회할 포스트의 참조 코드
     * @param page 페이지 번호 (0부터 시작)
     * @param pageSize 페이지당 댓글 수
     * @return 댓글 목록이 포함된 API 응답
     * @throws BusinessException 포스트를 찾을 수 없는 경우
     */
    @GetMapping("/posts/{refCode}/comments")
    public ResponseEntity<APIResponse<PostCommentsResponse>> getComments(
            @PathVariable String refCode,
            @RequestParam Integer page,
            @RequestParam Integer pageSize
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.success(getPostCommentsUsecase.getComments(refCode, page, pageSize)));
    }

    /**
     * 특정 포스트에 새로운 댓글을 생성합니다.
     *
     * 요청받은 정보로 새 댓글을 생성하고 생성된 댓글 정보를 반환합니다.
     * 포스트가 활성 상태일 때만 댓글을 생성할 수 있습니다.
     *
     * @param refCode 댓글을 작성할 포스트의 참조 코드
     * @param request 댓글 생성 요청 정보 (작성자 ID, 댓글 내용 포함)
     * @return 생성된 댓글 정보가 포함된 API 응답
     * @throws BusinessException 포스트를 찾을 수 없는 경우
     */
    @PostMapping("/posts/{refCode}/comments/")
    public ResponseEntity<APIResponse<CreateCommentResponse>> createComment(
            @PathVariable String refCode,
            @RequestBody CreateCommentRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.success(createCommentUsecase.createComment(refCode, request)));
    }

    /**
     * 댓글을 삭제합니다 (소프트 삭제).
     *
     * 댓글의 작성자만 삭제할 수 있으며, 실제로는 상태를 DELETED로 변경합니다.
     * 포스트와 댓글이 모두 활성 상태일 때만 삭제할 수 있습니다.
     *
     * @param refCode 댓글이 속한 포스트의 참조 코드
     * @param commentId 삭제할 댓글의 ID
     * @param memberId 삭제 요청자의 멤버 ID (작성자 검증용)
     * @return 성공 응답
     * @throws BusinessException 포스트나 댓글을 찾을 수 없는 경우, 또는 작성자가 아닌 경우
     */
    @DeleteMapping("/posts/{refCode}/comments/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(
            @PathVariable String refCode,
            @PathVariable Long commentId,
            @RequestParam Long memberId
    ) {
        deleteCommentUsecase.deleteComment(new DeleteCommentCommand(refCode, commentId, memberId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }
}
