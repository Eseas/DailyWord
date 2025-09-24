package com.dailyword.post.application.service.comment;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.application.usecase.comment.CreateCommentUsecase;
import com.dailyword.post.domain.model.Comment;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.adapter.in.facade.dto.CreateCommentRequest;
import com.dailyword.post.adapter.in.facade.dto.CreateCommentResponse;
import com.dailyword.post.repository.CommentRepository;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 생성 서비스
 *
 * 특정 포스트에 새로운 댓글을 생성하는 비즈니스 로직을 처리합니다.
 * 활성 상태의 포스트에만 댓글을 생성할 수 있으며,
 * Comment 도메인 모델을 사용하여 비즈니스 규칙을 적용합니다.
 * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CreateCommentService implements CreateCommentUsecase {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 특정 포스트에 새로운 댓글을 생성합니다.
     *
     * 주어진 포스트 참조 코드로 활성 상태의 포스트를 조회하고,
     * 요청 정보로 Comment 도메인 모델을 생성하여 댓글을 생성합니다.
     * 생성된 댓글 정보를 DTO로 변환하여 반환합니다.
     * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
     *
     * @param refCode 댓글을 작성할 포스트의 참조 코드
     * @param request 댓글 생성 요청 정보 (작성자 ID, 댓글 내용 포함)
     * @return 생성된 댓글 정보 DTO
     * @throws BusinessException 포스트를 찾을 수 없는 경우
     */
    @Override
    @Transactional
    public CreateCommentResponse createComment(String refCode, CreateCommentRequest request) {
        Long postId = postRepository.findByRefCodeAndStatus(refCode, PostStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST.getMessage()))
                .getId();

        Comment comment = new Comment();
        comment = comment.create(postId, request.getMemberId(), request.getContent());

        return CreateCommentResponse.toDto(comment);
    }
}
