package com.dailyword.post.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.application.usecase.GetMemberPKUseCase;
import com.dailyword.post.infrastructure.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetMemberPkService implements GetMemberPKUseCase {

    private final MemberRepository memberRepository;

    @Override
    public Long getMemberPKByRefCode(String refCode) {
        return memberRepository.findByRefCode(refCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER.getMessage()))
                .getId();
    }
}
