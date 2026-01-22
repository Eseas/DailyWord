package com.dailyword.member.application.service;

import com.dailyword.common.event.UserCreatedEvent;
import com.dailyword.common.kafka.EventPublisher;
import com.dailyword.common.kafka.KafkaTopics;
import com.dailyword.member.application.usecase.RegisterMemberUseCase;
import com.dailyword.member.application.usecase.command.RegisterMemberCommand;
import com.dailyword.member.domain.model.Member;
import com.dailyword.member.infrastructure.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterMemberService implements RegisterMemberUseCase {

    private final MemberRepository memberRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public Long register(RegisterMemberCommand command) {
        Member member = Member.create(
                command.getName(),
                command.getNickname(),
                command.getLoginId(),
                command.getEmail(),
                command.getSocialLoginType()
        );

        Member savedMember = memberRepository.save(member);

        publishUserCreatedEvent(savedMember);

        log.info("Member registered successfully - memberId: {}, loginId: {}",
                savedMember.getId(), savedMember.getLoginId());

        return savedMember.getId();
    }

    private void publishUserCreatedEvent(Member member) {
        UserCreatedEvent event = UserCreatedEvent.of(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getLoginId()
        );

        eventPublisher.publish(KafkaTopics.USER_EVENTS, String.valueOf(member.getId()), event);
    }
}
