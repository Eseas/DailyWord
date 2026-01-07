package com.dailyword.post.domain.model;


import com.dailyword.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private ReportStatus status;

    private PostReport(Long postId, ReportStatus status) {
        this.postId = postId;
        this.status = status;
    }

    public static PostReport reportPost(Post post) {
        return new PostReport(post.getId(), ReportStatus.APPLICATION);
    }
}
