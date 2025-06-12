package com.dailyword.member.domain.model;

import com.dailyword.common.domain.BaseUuidEntity;
import com.dailyword.member.domain.IsActive;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Getter
public class Member extends BaseUuidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String loginId;

    private LocalDate birthday;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

}
