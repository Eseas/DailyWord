package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.PostDeleteUsecase;
import com.dailyword.gateway.dto.post.PostDeleteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 삭제 서비스 (Gateway)
 * Gateway 모듈에서 게시글 삭제 비즈니스 로직을 담당하며, module-post와의 통신을 처리합니다.
 * Feign Client를 통해 module-post의 내부 API를 호출하여 게시글 삭제를 처리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostDeleteService implements PostDeleteUsecase {

    private final PostClient postClient;

    /**
     * 게시글 삭제
     * 기존 게시글을 삭제합니다.
     * module-post의 내부 API를 호출하여 게시글 삭제 요청을 처리하고 결과를 반환합니다.
     * 삭제 권한 검증 및 관련 데이터 정리는 module-post에서 처리됩니다.
     *
     * @param refCode 삭제할 게시글의 참조 코드
     * @param request 게시글 삭제 요청 데이터 (삭제 권한 확인용)
     * @return 삭제 결과 메시지
     */
    @Override
    public String delete(String refCode, PostDeleteRequest request) {
        return postClient.deletePost(refCode, request).getData();
    }
}
