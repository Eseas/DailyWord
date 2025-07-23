package com.dailyword.member.domain.model;

import com.dailyword.common.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "member_profile")
public class MemberProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "status_message")
    private String statusMessage;
}
