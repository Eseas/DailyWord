package com.dailyword.gateway.application.service.comments;

import com.dailyword.gateway.adapter.out.client.CommentClient;
import com.dailyword.gateway.application.usecase.comment.GetPostCommentUsecase;
import com.dailyword.gateway.dto.comment.PostCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 댑글 조회 서비스 (Gateway)
 * Gateway 모듈에서 게시글별 댑글 조회 비즈니스 로직을 담당하며, module-comment와의 통신을 처리합니다.
 * Feign Client를 통해 module-comment의 내부 API를 호출하여 게시글에 달린 댑글들을 페이지네이션으로 조회합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GetPostCommentService implements GetPostCommentUsecase {

    private final CommentClient commentClient;

    /**
     * 게시글 댑글 목록 조회
     * 특정 게시글에 달린 댑글들을 페이지네이션으로 조회합니다.
     * module-comment의 내부 API를 호출하여 댑글 목록과 페이지네이션 정보를 가져옵니다.
     *
     * @param postRefCode 댑글을 조회할 게시글의 참조 코드
     * @param page 페이지 번호 (0부터 시작)
     * @param pageSize 페이지당 댑글 수
     * @return 댑글 목록과 페이지네이션 정보
     */
    @Override
    public PostCommentResponse getComments(String postRefCode, Integer page, Integer pageSize) {
        return commentClient.getComments(postRefCode, page, pageSize).getData();
    }
}
