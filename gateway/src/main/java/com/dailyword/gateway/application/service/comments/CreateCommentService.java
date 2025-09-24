package com.dailyword.gateway.application.service.comments;

import com.dailyword.gateway.adapter.out.client.CommentClient;
import com.dailyword.gateway.application.usecase.comment.CreateCommentUsecase;
import com.dailyword.gateway.dto.comment.CreateCommentRequest;
import com.dailyword.gateway.dto.comment.CreateCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 댑글 생성 서비스 (Gateway)
 * Gateway 모듈에서 댑글 생성 비즈니스 로직을 담당하며, module-comment와의 통신을 처리합니다.
 * Feign Client를 통해 module-comment의 내부 API를 호출하여 새로운 댑글을 생성합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CreateCommentService implements CreateCommentUsecase {

    private final CommentClient commentClient;

    /**
     * 댑글 생성
     * 특정 게시글에 새로운 댑글을 생성합니다.
     * module-comment의 내부 API를 호출하여 댑글 생성 요청을 처리하고 생성된 댑글 정보를 반환합니다.
     *
     * @param refCode 댑글을 생성할 게시글의 참조 코드
     * @param request 댑글 생성 요청 데이터 (작성자 ID, 댑글 내용 등)
     * @return 생성된 댑글의 정보 (댑글 ID, 작성일시 등)
     */
    @Override
    public CreateCommentResponse createComment(String refCode, CreateCommentRequest request) {
        return commentClient.createComment(refCode, request).getData();
    }
}
