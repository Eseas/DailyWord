package com.dailyword.member.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.member.domain.model.Member;
import com.dailyword.member.dto.member.LoginDto;
import com.dailyword.member.infrastructure.db.repository.MemberRepository;
import com.dailyword.member.application.usecase.LoginUseCase;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {
    private final MemberRepository memberRepository;

    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public LoginDto.Response login(LoginDto.Request requestDto) {
        Member member = memberRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER.getMessage()));

        return LoginDto.Response.toDto(member);
    }
}
