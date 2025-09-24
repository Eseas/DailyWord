package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.PostCreateUsecase;
import com.dailyword.gateway.dto.post.CreatePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 생성 서비스 (Gateway)
 * Gateway 모듈에서 게시글 생성 비즈니스 로직을 담당하며, module-member와 module-post의 통신을 처리합니다.
 * Feign Client를 통해 회원 참조 코드를 내부 ID로 변환하고, module-post를 통해 새로운 게시글을 생성합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostCreateService implements PostCreateUsecase {

    private final PostClient postClient;
    private final MemberClient memberClient;

    /**
     * 게시글 생성
     * 새로운 게시글을 생성합니다.
     * 작성자의 참조 코드를 내부 ID로 변환한 후, module-post를 통해 게시글 생성 요청을 처리합니다.
     * 생성된 게시글의 참조 코드를 반환합니다.
     *
     * @param request 게시글 생성 요청 데이터 (제목, 내용, 작성자 참조 코드 등)
     * @return 생성된 게시글의 참조 코드
     */
    @Override
    public String createPost(CreatePostRequest request) {
        Long memberPK = memberClient.idByRefCode(request.getAuthorRefCode()).getData();

        return postClient.createPost(request.toCommand(memberPK)).getData();
    }
}
