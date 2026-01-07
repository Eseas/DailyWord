package com.dailyword.post.repository;

import com.dailyword.post.domain.model.PostReport;
import com.dailyword.post.domain.model.ReportStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport, Integer> {

    Integer countByPostIdAndRepostStatus(Long postId, ReportStatus reportStatus);

}
