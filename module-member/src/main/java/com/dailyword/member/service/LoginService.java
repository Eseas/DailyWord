package com.dailyword.member.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.member.domain.Member;
import com.dailyword.member.dto.member.LoginDto;
import com.dailyword.member.repository.MemberRepository;
import com.dailyword.member.usecase.LoginUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginDto.Response login(LoginDto.Request requestDto) {
        Member member = memberRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER.getMessage()));

        passwordEncoder.matches(requestDto.getPassword(), member.getPassword());

        return LoginDto.Response.toDto(member);
    }
}
