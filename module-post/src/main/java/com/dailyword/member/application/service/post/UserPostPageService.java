package com.dailyword.member.application.service.post;

import com.dailyword.member.adapter.in.facade.dto.MyPostPageResponse;
import com.dailyword.member.adapter.in.facade.dto.PostPageResponse;
import com.dailyword.member.application.usecase.post.UserPostPageUsecase;
import com.dailyword.member.domain.model.PostStatus;
import com.dailyword.member.repository.PostRepository;
import com.dailyword.member.repository.projection.PostListView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPostPageService implements UserPostPageUsecase {

    private final PostRepository postRepository;

    @Override
    public List<MyPostPageResponse> getUserPosts(Long memberId, Integer page, Integer size) {
        List<PostListView> myPostList = postRepository.findByMemberIdAndStatus(memberId, PostStatus.ACTIVE);

        return myPostList.stream().map(MyPostPageResponse::toDto).toList();
    }
}
