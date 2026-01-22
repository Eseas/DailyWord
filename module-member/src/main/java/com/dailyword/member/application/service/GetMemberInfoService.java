package com.dailyword.member.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.member.domain.model.Member;
import com.dailyword.member.dto.member.GetMemberInfo;
import com.dailyword.member.infrastructure.db.repository.MemberRepository;
import com.dailyword.member.application.usecase.GetMemberInfoUseCase;
import org.springframework.stereotype.Service;

/**
 * 회원 정보 조회 서비스
 * <p>
 * 회원의 상세 정보를 조회하는 비즈니스 로직을 담당합니다.
 * 회원 ID를 통해 데이터베이스에서 회원 정보를 검색하고,
 * 조회된 회원 도메인 객체를 DTO로 변환하여 반환합니다.
 * 존재하지 않는 회원에 대해서는 비즈니스 예외를 발생시킵니다.
 * </p>
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
public class GetMemberInfoService implements GetMemberInfoUseCase {

    private final MemberRepository memberRepository;

    /**
     * GetMemberInfoService 생성자
     *
     * @param memberRepository 회원 정보 접근을 위한 레포지토리
     */
    public GetMemberInfoService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 ID로 회원 정보를 조회합니다.
     * <p>
     * 주어진 회원 ID를 통해 데이터베이스에서 회원 정보를 검색합니다.
     * 조회된 회원 도메인 객체를 GetMemberInfo.Response DTO로 변환하여 반환합니다.
     * 회원이 존재하지 않는 경우 BusinessException을 발생시킵니다.
     * </p>
     *
     * @param memberId 조회할 회원의 고유 식별자
     * @return 회원 정보를 담은 응답 DTO
     * @throws BusinessException 회원이 존재하지 않는 경우
     */
    @Override
    public GetMemberInfo.Response getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        var response = GetMemberInfo.Response.toDto(member);

        return response;
    }
}
