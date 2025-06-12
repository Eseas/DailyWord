package com.dailyword.member.infrastructure.db.repository;

import com.dailyword.member.domain.IsActive;
import com.dailyword.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByIdAndIsActive(Long id, IsActive isActive);
}
