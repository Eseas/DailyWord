package com.dailyword.gateway.application.service.follow;

import com.dailyword.gateway.adapter.out.client.FollowClient;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.follow.UnFollowUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 언팔로우 실행 서비스 (Gateway)
 * Gateway 모듈에서 언팔로우 실행 비즈니스 로직을 담당하며, module-member와 module-follow의 통신을 처리합니다.
 * Feign Client를 통해 회원 참조 코드를 내부 ID로 변환하고, 팔로우 취소를 처리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UnFollowService implements UnFollowUsecase {

    private final MemberClient memberClient;
    private final FollowClient followClient;

    /**
     * 언팔로우 실행
     * 특정 회원에 대한 팔로우를 취소합니다.
     * 회원 참조 코드를 내부 ID로 변환한 후, module-follow를 통해 팔로우 관계를 제거합니다.
     * 팔로우 상태가 아닌 경우 예외가 발생합니다.
     *
     * @param followerRefCode 언팔로우를 실행할 회원의 참조 코드
     * @param followeeRefCode 언팔로우될 회원의 참조 코드
     */
    @Override
    public void unFollow(String followerRefCode, String followeeRefCode) {
        Long followerId = memberClient.idByRefCode(followerRefCode).getData();
        Long followeeId = memberClient.idByRefCode(followeeRefCode).getData();

        followClient.unFollow(followerId, followeeId);

        return;
    }
}
