package com.dailyword.common.domain;

import com.dailyword.common.util.UuidUtils;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Getter
@EnableJpaAuditing
public class BaseEntity extends BaseTimeEntity {
    @Column(updatable = false)
    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;
}
