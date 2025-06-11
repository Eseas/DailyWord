package com.dailyword.member.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.member.domain.IsActive;
import com.dailyword.member.domain.Member;
import com.dailyword.member.dto.member.PatchMemberInfo;
import com.dailyword.member.infrastructure.db.repository.MemberRepository;
import com.dailyword.member.application.usecase.PatchMemberInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatchMemberInfoService implements PatchMemberInfoUseCase {

    private final MemberRepository memberRepository;

    @Override
    public PatchMemberInfo.Response patchMemberInfo(Long memberId, PatchMemberInfo.Request request) {
        Member findMember = memberRepository.findByIdAndIsActive(memberId, IsActive.ACITVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER.getMessage()));

        Member editMember = findMember.editInfo(request.getNickname());

        return PatchMemberInfo.Response.toDto(editMember);
    }
}
