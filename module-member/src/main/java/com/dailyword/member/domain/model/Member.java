package com.dailyword.member.domain.model;

import com.dailyword.common.domain.BaseUuidEntity;
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

    private String nickname;

    private String loginId;

    private LocalDate birthday;

    private String email;

    @Enumerated(EnumType.STRING)
    private SocialLoginType socialLoginType;

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeInfo(String nickname) {
        setNickname(nickname);
    }
}
