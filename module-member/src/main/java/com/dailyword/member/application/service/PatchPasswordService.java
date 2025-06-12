package com.dailyword.member.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.member.domain.model.Member;
import com.dailyword.member.dto.member.PatchPassword;
import com.dailyword.member.infrastructure.db.repository.MemberRepository;
import com.dailyword.member.application.usecase.PatchPasswordUseCase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatchPasswordService implements PatchPasswordUseCase {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PatchPasswordService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void patchPassword(PatchPassword.Request request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER.getMessage()));

        String newPassword = passwordEncoder.encode(request.getNewPassword());

        member.changePassword(newPassword);

        memberRepository.save(member);
    }
}
