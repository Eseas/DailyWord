package com.dailyword.post.application.service.post;

import com.dailyword.post.application.usecase.post.PostUpdateUsecase;
import com.dailyword.post.application.usecase.command.UpdatePostCommand;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.common.response.ErrorCode.NOT_FOUND_POST;
import static com.dailyword.common.response.ErrorCode.NOT_YOUR_POST;

/**
 * 포스트 수정 서비스
 *
 * 기존 포스트의 내용을 수정하는 비즈니스 로직을 처리합니다.
 * 작성자 권한 검증을 통해 자신이 작성한 포스트만 수정할 수 있도록 보장합니다.
 * 포스트의 내용과 숨김 여부를 변경할 수 있으며,
 * 활성 상태의 포스트만 수정 처리할 수 있습니다.
 * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostUpdateService implements PostUpdateUsecase {

    private final PostRepository postRepository;

    /**
     * 포스트의 내용과 숨김 여부를 수정합니다.
     *
     * 주어진 참조 코드로 활성 상태의 포스트를 조회하고,
     * 작성자 권한을 검증한 후 포스트의 내용과 숨김 여부를 업데이트합니다.
     * JPA의 더티 체킹 기능을 활용하여 자동으로 데이터베이스에 반영합니다.
     * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
     *
     * @param command 포스트 수정 커맨드 (참조 코드, 새 내용, 숨김 여부, 수정자 ID 포함)
     * @return 수정된 포스트의 참조 코드
     * @throws IllegalArgumentException 포스트를 찾을 수 없거나 작성자가 아닌 경우
     */
    @Override
    @Transactional
    public String update(UpdatePostCommand command) {
        Post post = postRepository.findByRefCodeAndStatus(command.getRefCode(), PostStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));

        if (!post.getAuthorId().equals(command.getMemberId())) {
            throw new IllegalArgumentException(NOT_YOUR_POST.getMessage());
        }

        post.updateContent(command.getContent());
        post.updateIsHide(command.getIsHide());
        return post.getRefCode();
    }
}
