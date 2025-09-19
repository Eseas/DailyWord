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

@Service
@RequiredArgsConstructor
public class DeleteCommentService implements DeleteCommentUsecase {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

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
