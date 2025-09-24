package com.dailyword.gateway.application.service.follow;

import com.dailyword.gateway.adapter.out.client.FollowClient;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.follow.GetFollowCountUsecase;
import com.dailyword.gateway.dto.follow.GetFollowCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 팔로우 수 조회 서비스 (Gateway)
 * Gateway 모듈에서 팔로우 수 조회 비즈니스 로직을 담당하며, module-member와 module-follow의 통신을 처리합니다.
 * Feign Client를 통해 회원 참조 코드를 내부 ID로 변환하고, 해당 회원의 팔로워/팔로잉 수를 조회합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GetFollowCountService implements GetFollowCountUsecase {

    private final MemberClient memberClient;
    private final FollowClient followClient;

    /**
     * 팔로우 수 조회
     * 특정 회원의 팔로워 수와 팔로잉 수를 조회합니다.
     * 회원 참조 코드를 내부 ID로 변환한 후, module-follow를 통해 팔로우 통계 정보를 가져옵니다.
     *
     * @param memberRefCode 팔로우 수를 조회할 회원의 참조 코드
     * @return 팔로워 수와 팔로잉 수 정보
     */
    @Override
    public GetFollowCount getFollowCount(String memberRefCode) {
        Long memberId = memberClient.idByRefCode(memberRefCode).getData();

        return followClient.getFollowCount(memberId).getData();
    }
}
