package com.dailyword.post.application.service.post;

import com.dailyword.post.application.usecase.post.PostHideUsecase;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.common.response.ErrorCode.NOT_FOUND_POST;

/**
 * 포스트 숨김 처리 서비스
 *
 * 포스트의 숨김 상태를 토글하는 비즈니스 로직을 처리합니다.
 * 활성 상태의 포스트에 대해서만 숨김 상태를 변경할 수 있으며,
 * 현재 숨김 상태가 true라면 false로, false라면 true로 변경합니다.
 * JPA의 더티 체킹 기능을 활용하여 자동으로 데이터베이스에 반영합니다.
 * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostHideService implements PostHideUsecase {

    private final PostRepository postRepository;

    /**
     * 포스트의 숨김 상태를 토글합니다.
     *
     * 주어진 참조 코드로 활성 상태의 포스트를 조회하고,
     * 현재 숨김 상태의 반대값으로 업데이트합니다.
     * 숨김 상태가 true인 포스트는 공개로, false인 포스트는 숨김으로 변경됩니다.
     * JPA의 더티 체킹 기능을 활용하여 자동으로 데이터베이스에 반영합니다.
     * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
     *
     * @param postRefCode 숨김 상태를 변경할 포스트의 참조 코드
     * @return 변경된 포스트의 참조 코드
     * @throws IllegalArgumentException 포스트를 찾을 수 없는 경우
     */
    @Override
    @Transactional
    public String hidePost(String postRefCode) {
        Post post = postRepository.findByRefCodeAndStatus(postRefCode, PostStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));

        post.updateIsHide(!post.getIsHide());
        return post.getRefCode();
    }
}