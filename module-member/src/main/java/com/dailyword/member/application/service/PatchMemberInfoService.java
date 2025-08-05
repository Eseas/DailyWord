package com.dailyword.member.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.member.application.usecase.PatchMemberInfoUsecase;
import com.dailyword.member.domain.model.Member;
import com.dailyword.member.dto.member.PatchMemberInfo;
import com.dailyword.member.infrastructure.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatchMemberInfoService implements PatchMemberInfoUsecase {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void patchMemberInfo(String memberRefCode, PatchMemberInfo patchMemberInfo) {
        Member member = memberRepository.findByRefCode(memberRefCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER.getMessage()));

        member.changeInfo(patchMemberInfo.getNickname());

        memberRepository.save(member);
    }
}
