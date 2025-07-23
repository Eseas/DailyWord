package com.dailyword.member.application.service;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.member.domain.model.Member;
import com.dailyword.member.dto.member.GetMemberInfo;
import com.dailyword.member.infrastructure.db.repository.MemberRepository;
import com.dailyword.member.application.usecase.GetMemberInfoUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetMemberInfoService implements GetMemberInfoUseCase {

    private final MemberRepository memberRepository;

    public GetMemberInfoService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public GetMemberInfo.Response getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER.getMessage()));

        var response = GetMemberInfo.Response.toDto(member);

        return response;
    }
}
