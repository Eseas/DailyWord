package com.dailyword.member.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.member.application.usecase.GetMemberPKUseCase;
import com.dailyword.member.infrastructure.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 회원 PK 조회 서비스
 * <p>
 * 회원의 참조 코드를 통해 해당 회원의 데이터베이스 PK를 조회하는 비즈니스 로직을 담당합니다.
 * 내부 서비스 간 통신에서 회원의 고유 식별자가 필요할 때 사용됩니다.
 * 참조 코드로 회원을 찾지 못하는 경우 BusinessException을 발생시킵니다.
 * </p>
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GetMemberPkService implements GetMemberPKUseCase {

    private final MemberRepository memberRepository;

    /**
     * 참조 코드로 회원 PK를 조회합니다.
     * <p>
     * 주어진 참조 코드를 통해 데이터베이스에서 회원을 검색하고,
     * 조회된 회원의 데이터베이스 PK 값을 반환합니다.
     * 이 메서드는 내부 서비스 간 통신에서 회원의 고유 식별자가 필요할 때 사용됩니다.
     * 회원이 존재하지 않는 경우 BusinessException을 발생시킵니다.
     * </p>
     *
     * @param refCode 조회할 회원의 참조 코드
     * @return 회원의 데이터베이스 PK 값
     * @throws BusinessException 회원이 존재하지 않는 경우
     */
    @Override
    public Long getMemberPKByRefCode(String refCode) {
        return memberRepository.findByRefCode(refCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER.getMessage()))
                .getId();
    }
}
