package com.dailyword.post.application.service.post;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.post.application.usecase.post.PostReportUsecase;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.domain.model.PostReport;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.domain.model.ReportStatus;
import com.dailyword.post.repository.PostReportRepository;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostReportService implements PostReportUsecase {

    private final PostRepository postRepository;
    private final PostReportRepository postReportRepository;

    @Override
    @Transactional
    public Long postReport(String postRefCode) {
        Post post = postRepository.findByRefCodeAndStatus(postRefCode, PostStatus.ACTIVE)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST.getMessage()));

        PostReport postReport = PostReport.reportPost(post);

        postReportRepository.save(postReport);

        Integer reportCount = postReportRepository
            .countByPostIdAndRepostStatus(post.getId(), ReportStatus.APPLICATION);

        if(reportCount > 5) {
            post.hideTemporary();
        }

        return postReport.getId();
    }
}
