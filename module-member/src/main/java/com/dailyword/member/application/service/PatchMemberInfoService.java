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

/**
 * 회원 정보 수정 서비스
 * <p>
 * 회원의 정보를 수정하는 비즈니스 로직을 담당합니다.
 * 회원의 참조 코드를 통해 해당 회원을 찾고,
 * 요청된 정보로 회원의 닉네임 등의 정보를 업데이트합니다.
 * 모든 수정 작업은 트랜잭션 내에서 처리되어 데이터 일관성을 보장합니다.
 * </p>
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PatchMemberInfoService implements PatchMemberInfoUsecase {

    private final MemberRepository memberRepository;

    /**
     * 회원 정보를 수정합니다.
     * <p>
     * 회원의 참조 코드를 통해 해당 회원을 조회하고,
     * 전달받은 정보로 회원의 닉네임을 업데이트합니다.
     * 수정된 회원 정보는 데이터베이스에 자동으로 저장됩니다.
     * 회원이 존재하지 않는 경우 BusinessException을 발생시킵니다.
     * </p>
     *
     * @param memberRefCode 수정할 회원의 참조 코드
     * @param patchMemberInfo 수정할 회원 정보 (닉네임 등)
     * @throws BusinessException 회원이 존재하지 않는 경우
     */
    @Override
    @Transactional
    public void patchMemberInfo(String memberRefCode, PatchMemberInfo patchMemberInfo) {
        Member member = memberRepository.findByRefCode(memberRefCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        member.changeInfo(patchMemberInfo.getNickname());

        memberRepository.save(member);
    }
}
