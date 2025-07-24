package com.dailyword.follow.domain.model;

import com.dailyword.common.domain.BaseTimeEntity;
import com.dailyword.follow.domain.constant.FollowStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "follow")
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followerId;
    private Long followeeId;

    private FollowStatus followStatus;
}
