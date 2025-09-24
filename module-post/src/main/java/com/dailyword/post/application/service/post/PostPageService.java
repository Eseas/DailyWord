package com.dailyword.post.application.service.post;

import com.dailyword.post.application.usecase.post.PostPageUsecase;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.adapter.in.facade.dto.PostPageResponse;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 포스트 페이지네이션 조회 서비스
 *
 * 전체 포스트 목록을 페이지담위로 조회하는 비즈니스 로직을 처리합니다.
 * JPA Repository를 사용하여 데이터베이스에서 포스트 데이터를 조회하고,
 * 생성일 기준 내림차순으로 정렬하여 반환합니다.
 * 읽기 전용 트랜잭션을 사용하여 성능을 최적화합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostPageService implements PostPageUsecase {

    private final PostRepository postRepository;

    /**
     * 전체 포스트 목록을 페이지담위로 조회합니다.
     *
     * 주어진 페이지 정보를 기반으로 포스트들을 조회하고,
     * 생성일 기준 내림차순으로 정렬하여 내보냅니다.
     * 리포지토리의 findAll 메소드를 사용하여 모든 상태의 포스트를 조회합니다.
     *
     * @param page 페이지 번호 (0부터 시작)
     * @param size 한 페이지에 표시할 포스트 수
     * @return 포스트 목록을 DTO로 변환한 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<PostPageResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAll(pageable);

        List<PostPageResponse> result = posts.stream()
                .map(PostPageResponse::toDto).toList();

        return result;
    }
}
