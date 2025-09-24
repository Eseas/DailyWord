package com.dailyword.gateway.application.service.follow;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.gateway.adapter.out.client.FollowClient;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.follow.GetFollowingListUsecase;
import com.dailyword.gateway.dto.follow.GetFollowingList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 팔로잉 목록 조회 서비스 (Gateway)
 * Gateway 모듈에서 팔로잉 목록 조회 비즈니스 로직을 담당하며, module-member와 module-follow의 통신을 처리합니다.
 * Feign Client를 통해 회원 참조 코드를 내부 ID로 변환하고, 해당 회원이 팔로우하고 있는 회원들의 목록을 조회합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GetFollowListService implements GetFollowingListUsecase {

    private final MemberClient memberClient;
    private final FollowClient followClient;

    /**
     * 팔로잉 목록 조회
     * 특정 회원이 팔로우하고 있는 다른 회원들의 목록을 페이지네이션으로 조회합니다.
     * 회원 참조 코드를 내부 ID로 변환한 후, module-follow를 통해 팔로잉 목록과 페이지네이션 정보를 가져옵니다.
     *
     * @param memberRefCode 팔로잉 목록을 조회할 회원의 참조 코드
     * @param page 페이지 번호 (0부터 시작)
     * @return 팔로잉 목록과 페이지네이션 정보
     */
    @Override
    public PageResponse<GetFollowingList> getFollowList(String memberRefCode, Integer page) {
        Long memberId = memberClient.idByRefCode(memberRefCode).getData();

        return followClient.getFollowingList(memberId, page).getData();
    }
}
