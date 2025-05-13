package com.dailyword.member.domain;

import com.dailyword.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String loginId;

    private String password;

    private LocalDate birthday;

    private String email;

    @Enumerated(EnumType.ORDINAL)
    private MemberRole role;

    @Enumerated(EnumType.ORDINAL)
    private IsActive isActive;

    private Member(String name, String loginId, String password, LocalDate birthday, String email) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
    }

    public static Member register(String name, String loginId, String password, LocalDate birthday, String email) {
        return new Member(name, loginId, password, birthday, email);
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
