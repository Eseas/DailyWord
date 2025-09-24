package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.PostPageUsecase;
import com.dailyword.gateway.dto.post.PostPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 게시글 목록 조회 서비스 (Gateway)
 * Gateway 모듈에서 게시글 목록 조회 비즈니스 로직을 담당하며, module-post와의 통신을 처리합니다.
 * Feign Client를 통해 module-post의 내부 API를 호출하여 게시글들을 페이지네이션으로 조회합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostPageService implements PostPageUsecase {

    private final PostClient postClient;

    /**
     * 게시글 목록 조회
     * 전체 게시글을 페이지네이션으로 조회합니다.
     * module-post의 내부 API를 호출하여 공개 게시글들을 최신 순으로 가져옵니다.
     * 숨꺨진 게시글이나 비공개 게시글은 제외됩니다.
     *
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지당 게시글 수
     * @return 게시글 목록과 기본 정보
     */
    @Override
    public List<PostPageResponse> getPosts(int page, int size) {
        List<PostPageResponse> response = postClient.getPosts(page, size).getData();
        return response;
    }
}
