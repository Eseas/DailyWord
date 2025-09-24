package com.dailyword.post.application.service.comment;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.application.usecase.command.DeleteCommentCommand;
import com.dailyword.post.application.usecase.comment.DeleteCommentUsecase;
import com.dailyword.post.domain.model.Comment;
import com.dailyword.post.domain.model.CommentStatus;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.repository.CommentRepository;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 삭제 서비스
 *
 * 댓글을 삭제하는 비즈니스 로직을 처리합니다.
 * 소프트 삭제 방식을 사용하여 실제 데이터를 삭제하지 않고 상태만 변경합니다.
 * 포스트와 댓글이 모두 활성 상태이며, 댓글의 작성자와 요청자가 일치할 때만 삭제할 수 있습니다.
 * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DeleteCommentService implements DeleteCommentUsecase {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글을 삭제합니다 (소프트 삭제).
     *
     * 주어진 포스트 참조 코드로 활성 상태의 포스트를 조회하고,
     * 해당 포스트에서 주어진 댓글 ID와 일치하는 활성 상태의 댓글을 조회합니다.
     * 댓글의 작성자와 삭제 요청자가 일치하는지 검증한 후 댓글의 상태를 DELETED로 변경합니다.
     * 실제 데이터는 삭제하지 않으며, 상태만 변경하는 소프트 삭제를 사용합니다.
     * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
     *
     * @param command 댓글 삭제 커맨드 (포스트 참조 코드, 댓글 ID, 요청자 ID 포함)
     * @throws BusinessException 포스트나 댓글을 찾을 수 없는 경우
     */
    @Override
    @Transactional
    public void deleteComment(DeleteCommentCommand command) {
        Long postId = postRepository.findByRefCodeAndStatus(command.getPostRefCode(), PostStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST.getMessage()))
                .getId();

        Comment comment = commentRepository.findByCommentIdAndPostIdAndStatus(command.getCommentId(), postId, CommentStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT.getMessage()));

        comment.delete();
    }
}
