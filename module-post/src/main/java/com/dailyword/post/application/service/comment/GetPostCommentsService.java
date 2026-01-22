package com.dailyword.post.application.service.comment;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.application.usecase.comment.GetPostCommentsUsecase;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.adapter.in.facade.dto.PostCommentsResponse;
import com.dailyword.post.repository.CommentRepository;
import com.dailyword.post.repository.PostRepository;
import com.dailyword.post.repository.projection.CommentView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dailyword.post.domain.model.CommentStatus.*;

/**
 * 포스트 댓글 조회 서비스
 *
 * 특정 포스트의 댓글 목록을 페이지네이션으로 조회하는 비즈니스 로직을 처리합니다.
 * 활성 상태의 포스트에 대해서만 댓글을 조회하며,
 * CommentView projection을 사용하여 필요한 데이터만 효율적으로 조회합니다.
 * 읽기 전용 트랜잭션을 사용하여 성능을 최적화합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GetPostCommentsService implements GetPostCommentsUsecase {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 특정 포스트의 댓글 목록을 페이지네이션으로 조회합니다.
     *
     * 주어진 포스트 참조 코드로 활성 상태의 포스트를 조회하고,
     * 해당 포스트에 달린 활성 상태의 댓글들을 페이지담위로 조회합니다.
     * CommentView projection을 사용하여 필요한 데이터만 효율적으로 조회하고,
     * 읽기 전용 트랜잭션을 사용하여 데이터베이스 성능을 최적화합니다.
     *
     * @param refCode 댓글을 조회할 포스트의 참조 코드
     * @param page 페이지 번호 (0부터 시작)
     * @param pageSize 한 페이지에 표시할 댓글 수
     * @return 포스트 ID와 댓글 목록을 포함한 DTO
     * @throws BusinessException 포스트를 찾을 수 없는 경우
     */
    @Override
    @Transactional(readOnly = true)
    public PostCommentsResponse getComments(String refCode, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Long postId = postRepository.findByRefCodeAndStatus(refCode, PostStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST))
                .getId();

        Page<CommentView> commentViews = commentRepository.getCommentsByPostId(pageable, postId, ACTIVE);

        return PostCommentsResponse.toDto(postId, commentViews.getContent());
    }
}
