package com.dailyword.post.application.service.post;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.post.adapter.in.facade.dto.MyPostPageResponse;
import com.dailyword.post.application.usecase.post.UserPostPageUsecase;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.repository.PostRepository;
import com.dailyword.post.repository.projection.PostListView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 사용자별 포스트 페이지네이션 조회 서비스
 *
 * 특정 사용자가 작성한 포스트 목록을 페이지담위로 조회하는 비즈니스 로직을 처리합니다.
 * 활성 상태의 포스트만 조회하며, PostListView projection을 사용하여
 * 필요한 데이터만 조회하여 성능을 최적화합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UserPostPageService implements UserPostPageUsecase {

    private final PostRepository postRepository;

    /**
     * 특정 사용자가 작성한 포스트 목록을 페이지담위로 조회합니다.
     *
     * 주어진 멤버 ID와 일치하는 활성 상태의 포스트들을 조회하고,
     * 생성일 기준 내림차순으로 정렬하여 반환합니다.
     * PostListView projection을 사용하여 필요한 데이터만 조회합니다.
     *
     * @param memberId 조회할 사용자의 ID
     * @param page 페이지 번호 (0부터 시작)
     * @param size 한 페이지에 표시할 포스트 수
     * @return 사용자 포스트 목록과 페이지 정보를 포함한 PageResponse
     */
    @Override
    public PageResponse<MyPostPageResponse> getUserPosts(Long memberId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<PostListView> myPostList = postRepository.findByMemberIdAndStatus(pageable, memberId, PostStatus.ACTIVE);

        return PageResponse.of(myPostList.stream().map(MyPostPageResponse::toDto).toList(), myPostList);
    }
}
