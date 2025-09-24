package com.dailyword.gateway.application.service.post;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.GetUserPostUsecase;
import com.dailyword.gateway.dto.post.MyPostPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 사용자 게시글 목록 조회 서비스 (Gateway)
 * Gateway 모듈에서 사용자별 게시글 목록 조회 비즈니스 로직을 담당하며, module-member와 module-post의 통신을 처리합니다.
 * Feign Client를 통해 회원 참조 코드를 내부 ID로 변환하고, 해당 사용자가 작성한 게시글 목록을 페이지네이션으로 조회합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class getUserPostList implements GetUserPostUsecase {

    private final MemberClient memberClient;
    private final PostClient postClient;

    /**
     * 사용자 게시글 목록 조회
     * 특정 사용자가 작성한 게시글들을 페이지네이션으로 조회합니다.
     * 회원 참조 코드를 내부 ID로 변환한 후, module-post를 통해 해당 사용자의 게시글 목록을 가져옵니다.
     * 마이페이지에서 내가 작성한 게시글 목록을 볼 때 주로 사용됩니다.
     *
     * @param memberRefCode 게시글을 조회할 회원의 참조 코드
     * @param page 페이지 번호 (0부터 시작)
     * @param pageSize 페이지당 게시글 수
     * @return 사용자의 게시글 목록과 페이지네이션 정보
     */
    @Override
    public PageResponse<MyPostPageResponse> getMyPostList(String memberRefCode, int page, int pageSize) {
        Long memberId = memberClient.idByRefCode(memberRefCode).getData();

        PageResponse<MyPostPageResponse> usersPost = postClient.getUserPosts(memberId, page, pageSize).getData();

        return usersPost;
    }
}
