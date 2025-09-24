package com.dailyword.post.application.service.post;

import com.dailyword.post.application.usecase.post.PostCreateUsecase;
import com.dailyword.post.application.usecase.command.CreatePostCommand;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 포스트 생성 서비스
 *
 * 새로운 포스트를 생성하는 비즈니스 로직을 처리합니다.
 * 포스트 도메인 모델을 사용하여 비즈니스 규칙을 적용하고,
 * 트랜잭션 처리를 통해 데이터 일관성을 보장합니다.
 * 작성자 ID, 내용, 숨김 여부를 설정하여 포스트를 생성합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PostCreateService implements PostCreateUsecase {

    private final PostRepository postRepository;

    /**
     * 새로운 포스트를 생성합니다.
     *
     * 커맨드 객체에서 전달받은 정보로 Post 도메인 모델을 생성하고,
     * 리포지토리를 통해 데이터베이스에 저장한 후 참조 코드를 반환합니다.
     * 트랜잭션을 사용하여 데이터 일관성을 보장합니다.
     *
     * @param command 포스트 생성 커맨드 (작성자 ID, 내용, 숨김 여부 포함)
     * @return 생성된 포스트의 참조 코드
     */
    @Override
    @Transactional
    public String createPost(CreatePostCommand command) {

        Post post = Post.create(command.getAuthorId(), command.getContent(), command.getIsHide());

        return postRepository.save(post).getRefCode();
    }
}
