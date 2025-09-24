package com.dailyword.post.application.service.post;

import com.dailyword.post.application.usecase.post.PostReadUsecase;
import com.dailyword.post.adapter.in.facade.dto.PostDetailResponse;
import com.dailyword.post.repository.PostRepository;
import com.dailyword.post.repository.projection.PostView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.common.response.ErrorCode.NOT_FOUND_POST;
import static com.dailyword.post.domain.model.PostStatus.*;

/**
 * 포스트 조회 서비스
 *
 * 특정 포스트의 상세 정보를 조회하는 비즈니스 로직을 처리합니다.
 * PostView projection을 사용하여 포스트 상세 정보를 효율적으로 조회하고,
 * 활성 상태의 포스트만 조회할 수 있도록 제한합니다.
 * 읽기 전용 트랜잭션을 사용하여 성능을 최적화합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostReadService implements PostReadUsecase {

    private final PostRepository postRepository;

    /**
     * 포스트 참조 코드로 특정 포스트의 상세 정보를 조회합니다.
     *
     * 주어진 참조 코드로 활성 상태의 포스트를 조회하고,
     * PostView projection을 사용하여 필요한 데이터만 효율적으로 조회합니다.
     * 읽기 전용 트랜잭션을 사용하여 데이터베이스 성능을 최적화합니다.
     *
     * @param postRefCode 조회할 포스트의 참조 코드
     * @return 포스트 상세 정보 DTO
     * @throws IllegalArgumentException 포스트를 찾을 수 없는 경우
     */
    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPost(String postRefCode) {
        PostView post = postRepository.findPostDetailByRefCode(postRefCode, ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));

        return PostDetailResponse.toDto(post);
    }
}
