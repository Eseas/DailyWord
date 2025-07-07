package com.dailyword.post.application.service.comment;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.application.usecase.comment.CreateCommentUsecase;
import com.dailyword.post.domain.model.Comment;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.facade.dto.CreateCommentRequest;
import com.dailyword.post.facade.dto.CreateCommentResponse;
import com.dailyword.post.repository.CommentRepository;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCommentService implements CreateCommentUsecase {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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
