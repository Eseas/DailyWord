package com.dailyword.member.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.member.domain.model.Member;
import com.dailyword.member.dto.member.LoginDto;
import com.dailyword.member.infrastructure.db.repository.MemberRepository;
import com.dailyword.member.application.usecase.LoginUseCase;
import org.springframework.stereotype.Service;

/**
 * 회원 로그인 서비스
 * <p>
 * 회원의 로그인 처리를 담당하는 비즈니스 로직을 구현합니다.
 * 로그인 ID를 통해 회원을 인증하고 로그인 처리를 수행합니다.
 * 인증이 성공한 경우 회원 정보를 포함한 응답을 반환하며,
 * 인증에 실패한 경우 BusinessException을 발생시킵니다.
 * </p>
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
public class LoginService implements LoginUseCase {
    private final MemberRepository memberRepository;

    /**
     * LoginService 생성자
     *
     * @param memberRepository 회원 정보 접근을 위한 레포지토리
     */
    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 로그인을 처리합니다.
     * <p>
     * 주어진 로그인 ID를 통해 데이터베이스에서 회원을 검색합니다.
     * 회원이 존재하는 경우 로그인 성공 응답을 생성하여 반환합니다.
     * 회원이 존재하지 않는 경우 BusinessException을 발생시킵니다.
     * </p>
     *
     * @param requestDto 로그인 요청 정보 (로그인 ID 포함)
     * @return 로그인 성공 시 회원 정보를 포함한 응답 DTO
     * @throws BusinessException 회원이 존재하지 않는 경우
     */
    public LoginDto.Response login(LoginDto.Request requestDto) {
        Member member = memberRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        return LoginDto.Response.toDto(member);
    }
}
