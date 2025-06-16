package com.dailyword.post.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.domain.model.Member;
import com.dailyword.post.dto.member.LoginDto;
import com.dailyword.post.infrastructure.db.repository.MemberRepository;
import com.dailyword.post.application.usecase.LoginUseCase;
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
