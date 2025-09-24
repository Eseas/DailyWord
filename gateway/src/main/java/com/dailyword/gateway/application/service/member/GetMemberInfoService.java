package com.dailyword.gateway.application.service.member;

import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.member.GetMemberInfoUsecase;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 정보 조회 서비스 (Gateway)
 * Gateway 모듈에서 회원 정보 조회 비즈니스 로직을 담당하며, module-member와의 통신을 처리합니다.
 * Feign Client를 통해 module-member의 내부 API를 호출하여 회원의 공개 프로필 정보를 조회합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GetMemberInfoService implements GetMemberInfoUsecase {

    private final MemberClient memberClient;

    /**
     * 회원 정보 조회
     * 회원 참조 코드를 통해 특정 회원의 공개 프로필 정보를 조회합니다.
     * module-member의 내부 API를 호출하여 회원의 기본 정보(닉네임, 프로필 이미지 등)를 가져옵니다.
     *
     * @param memberRefCode 조회할 회원의 참조 코드
     * @return 회원의 공개 프로필 정보
     */
    @Override
    public GetMemberInfo getMemberInfo(String memberRefCode) {
        return memberClient.getMemberInfo(memberRefCode).getData();
    }
}
