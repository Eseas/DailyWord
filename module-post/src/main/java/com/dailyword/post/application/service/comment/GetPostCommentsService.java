package com.dailyword.post.application.service.comment;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.application.usecase.comment.GetPostCommentsUsecase;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.facade.dto.PostCommentsResponse;
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

@Service
@RequiredArgsConstructor
public class GetPostCommentsService implements GetPostCommentsUsecase {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public PostCommentsResponse getComments(String refCode, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Long postId = postRepository.findByRefCodeAndStatus(refCode, PostStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST.getMessage()))
                .getId();

        Page<CommentView> commentViews = commentRepository.getCommentsByPostId(pageable, postId, ACTIVE);

        return new PostCommentsResponse().toDto(postId, commentViews.getContent());
    }
}
