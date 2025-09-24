package com.dailyword.post.application.service.post;

import com.dailyword.post.application.usecase.post.PostDeleteUsecase;
import com.dailyword.post.application.usecase.command.DeletePostCommand;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.common.response.ErrorCode.NOT_FOUND_POST;
import static com.dailyword.common.response.ErrorCode.NOT_YOUR_POST;

/**
 * 포스트 삭제 서비스
 *
 * 포스트를 삭제하는 비즈니스 로직을 처리합니다.
 * 소프트 삭제 방식을 사용하여 실제 데이터를 삭제하지 않고 상태만 변경합니다.
 * 작성자 권한 검증을 통해 자신이 작성한 포스트만 삭제할 수 있도록 보장합니다.
 * 활성 상태의 포스트만 삭제 처리할 수 있습니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostDeleteService implements PostDeleteUsecase {

    private final PostRepository postRepository;

    /**
     * 포스트를 삭제합니다 (소프트 삭제).
     *
     * 주어진 참조 코드로 활성 상태의 포스트를 조회하고,
     * 작성자 권한을 검증한 후 포스트의 상태를 DELETED로 변경합니다.
     * 실제 데이터는 삭제하지 않으며, 상태만 변경하는 소프트 삭제를 사용합니다.
     * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
     *
     * @param command 포스트 삭제 커맨드 (참조 코드, 삭제 요청자 ID 포함)
     * @return 삭제된 포스트의 참조 코드
     * @throws IllegalArgumentException 포스트를 찾을 수 없거나 작성자가 아닌 경우
     */
    @Override
    @Transactional
    public String delete(DeletePostCommand command) {
        Post post = postRepository.findByRefCodeAndStatus(command.getRefCode(), PostStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));

        if (!post.getAuthorId().equals(command.getMemberId())) {
            throw new IllegalArgumentException(NOT_YOUR_POST.getMessage());
        }

        post.delete();
        return post.getRefCode();
    }
}

