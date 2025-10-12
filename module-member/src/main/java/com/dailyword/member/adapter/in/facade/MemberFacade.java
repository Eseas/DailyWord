package com.dailyword.member.adapter.in.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.member.application.usecase.GetMemberInfoUseCase;
import com.dailyword.member.application.usecase.GetMemberPKUseCase;
import com.dailyword.member.application.usecase.LoginUseCase;
import com.dailyword.member.dto.member.GetMemberInfo;
import com.dailyword.member.dto.member.LoginDto;
import com.dailyword.member.dto.member.PatchMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 관리를 위한 REST API 파사드 클래스
 * <p>
 * 이 클래스는 회원 관련 모든 REST API 엔드포인트를 제공합니다.
 * 회원 정보 조회, 로그인, 회원 정보 수정 등의 기능을 포함하며,
 * 내부 서비스 간 통신을 위한 내부 API로 구성되어 있습니다.
 * </p>
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class MemberFacade {

    private final LoginUseCase loginUseCase;
    private final GetMemberInfoUseCase getMemberInfoUseCase;
    private final GetMemberPKUseCase getMemberPKUseCase;

    /**
     * 참조 코드로 회원 PK를 조회합니다.
     * <p>
     * 내부 서비스에서 회원의 고유 식별자가 필요할 때 사용되는 API입니다.
     * 참조 코드를 통해 해당 회원의 데이터베이스 PK 값을 반환합니다.
     * </p>
     *
     * @param refCode 회원의 참조 코드
     * @return 회원의 PK 값을 포함한 API 응답
     */
    @GetMapping("/internal/members/id-by-ref-code/{refCode}")
    public ResponseEntity<APIResponse<Long>> getMemberPK(
            @PathVariable String refCode
    ) {
        Long memberId = getMemberPKUseCase.getMemberPKByRefCode(refCode);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(memberId));
    }

    /**
     * 회원 ID로 회원 정보를 조회합니다.
     * <p>
     * 내부 서비스에서 특정 회원의 상세 정보가 필요할 때 사용되는 API입니다.
     * 회원의 PK를 통해 해당 회원의 모든 정보를 조회하여 반환합니다.
     * </p>
     *
     * @param id 조회할 회원의 PK
     * @return 회원 정보를 포함한 API 응답
     */
    @GetMapping("/internal/members/{id}")
    public ResponseEntity<APIResponse<GetMemberInfo.Response>> getMemberInfo(
            @PathVariable Long id
    ) {
        GetMemberInfo.Response response = getMemberInfoUseCase.getMemberInfo(id);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(response));
    }

    /**
     * 회원 로그인을 처리합니다.
     * <p>
     * 로그인 ID를 통해 회원을 인증하고 로그인 처리를 수행합니다.
     * 성공 시 회원 정보를 포함한 응답을 반환합니다.
     * </p>
     *
     * @param requestDto 로그인 요청 정보 (로그인 ID 포함)
     * @return 로그인 결과를 포함한 API 응답
     */
    @PostMapping("/internal/auth/login")
    public ResponseEntity<APIResponse<LoginDto.Response>> login(
            LoginDto.Request requestDto
    ) {
        LoginDto.Response response = loginUseCase.login(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(response));
    }

    /**
     * 회원 정보를 수정합니다.
     * <p>
     * 회원의 참조 코드를 통해 해당 회원을 식별하고,
     * 요청된 정보로 회원 정보를 업데이트합니다.
     * 현재는 응답 처리가 구현되지 않은 상태입니다.
     * </p>
     *
     * @param memberRefCode 수정할 회원의 참조 코드
     * @param patchMemberInfo 수정할 회원 정보
     * @return 수정 결과를 포함한 API 응답
     */
    @PatchMapping("/internal/members/{memberRefCode}/info")
    public ResponseEntity<APIResponse> updateMemberInfo(
            @PathVariable String memberRefCode,
            @RequestBody PatchMemberInfo patchMemberInfo
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }
}