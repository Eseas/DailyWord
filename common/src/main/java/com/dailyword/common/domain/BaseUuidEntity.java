package com.dailyword.common.domain;

import com.dailyword.common.util.UuidUtils;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
public class BaseUuidEntity {
    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(name = "ref_code", nullable = false, unique = true, length = 10)
    private String refCode;

    @Column(updatable = false)
    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;

    @PrePersist
    protected void onCreate() {
        if (this.uuid == null) {
            this.uuid = UuidUtils.generateUuid();
        }

        if (this.refCode == null) {
            this.refCode = UuidUtils.generateRandomRefCode(10); // 또는 generateShortRefCodeFromUuid()
        }
    }
}
