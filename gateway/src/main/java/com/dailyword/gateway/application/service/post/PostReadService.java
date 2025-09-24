package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.PostReadUsecase;
import com.dailyword.gateway.dto.post.PostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 상세 조회 서비스 (Gateway)
 * Gateway 모듈에서 게시글 상세 조회 비즈니스 로직을 담당하며, module-post와의 통신을 처리합니다.
 * Feign Client를 통해 module-post의 내부 API를 호출하여 특정 게시글의 상세 정보를 조회합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostReadService implements PostReadUsecase {

    private final PostClient postClient;

    /**
     * 게시글 상세 조회
     * 특정 게시글의 상세 정보를 조회합니다.
     * module-post의 내부 API를 호출하여 게시글 내용, 작성자 정보, 생성일시 등의 상세 데이터를 가져옵니다.
     *
     * @param postRefCode 조회할 게시글의 참조 코드
     * @return 게시글의 상세 정보
     */
    @Override
    public PostDetailResponse getPost(String postRefCode) {
        return postClient.getPost(postRefCode).getData();
    }
}