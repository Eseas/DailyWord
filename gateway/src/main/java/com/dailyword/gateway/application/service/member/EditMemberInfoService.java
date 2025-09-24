package com.dailyword.gateway.application.service.member;

import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.member.EditMemberInfoUsecase;
import com.dailyword.gateway.dto.member.PatchMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 정보 수정 서비스 (Gateway)
 * Gateway 모듈에서 회원 정보 수정 비즈니스 로직을 담당하며, module-member와의 통신을 처리합니다.
 * Feign Client를 통해 module-member의 내부 API를 호출하여 회원의 프로필 정보를 수정합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class EditMemberInfoService implements EditMemberInfoUsecase {

    private final MemberClient memberClient;

    /**
     * 회원 정보 수정
     * 회원의 프로필 정보를 수정합니다.
     * 닉네임, 프로필 이미지, 소개글 등의 정보를 변경할 수 있으며,
     * module-member의 내부 API를 통해 데이터베이스에 내용을 반영합니다.
     *
     * @param memberRefCode 정보를 수정할 회원의 참조 코드
     * @param request 수정할 회원 정보 데이터
     */
    @Override
    public void editInfo(String memberRefCode, PatchMemberInfo.Request request) {
        memberClient.patchMemberInfo(memberRefCode, request);
    }
}
