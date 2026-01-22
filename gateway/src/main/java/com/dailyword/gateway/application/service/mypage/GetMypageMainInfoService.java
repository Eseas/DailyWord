package com.dailyword.gateway.application.service.mypage;

import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.mypage.GetMypageMainInfoUsecase;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import com.dailyword.gateway.dto.mypage.MypageMainResponse;
import com.dailyword.gateway.dto.mypage.QtProgressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 마이페이지 메인 정보 조회 서비스 (Gateway)
 * Gateway 모듈에서 마이페이지 메인 화면 비즈니스 로직을 담당하며, 여러 모듈과의 통신을 처리합니다.
 * module-member와 module-post를 연동하여 회원 기본 정보와 작성 현황 데이터를 통합하여 제공합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GetMypageMainInfoService implements GetMypageMainInfoUsecase {

    private final MemberClient memberClient;
    private final PostClient postClient;

    /**
     * 마이페이지 메인 정보 조회
     * 회원의 마이페이지 메인 화면에 필요한 통합 정보를 조회합니다.
     * 회원 기본 정보(닉네임, 프로필 이미지 등)와 작성 현황 데이터를 합쳐서 반환합니다.
     * 여러 모듈에서 데이터를 가져와 통합하여 마이페이지 전용 응답 객체로 구성합니다.
     *
     * @param refCode 마이페이지 정보를 조회할 회원의 참조 코드
     * @return 마이페이지 메인 화면에 필요한 회원 정보 및 통계 데이터
     */
    @Override
    public MypageMainResponse getMypageMainInfo(String refCode) {
        Long memberId = memberClient.idByRefCode(refCode).getData();

        GetMemberInfo memberInfo = memberClient.getMemberInfo(refCode).getData();

        QtProgressResponse qtProgress = postClient.getQtProgressDates(memberId).getData();

        return MypageMainResponse.create(memberInfo, qtProgress);
    }
}
