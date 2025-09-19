package com.dailyword.post.application.service.post;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.post.adapter.in.facade.dto.MyPostPageResponse;
import com.dailyword.post.application.usecase.post.UserPostPageUsecase;
import com.dailyword.post.domain.model.PostStatus;
import com.dailyword.post.repository.PostRepository;
import com.dailyword.post.repository.projection.PostListView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPostPageService implements UserPostPageUsecase {

    private final PostRepository postRepository;

    @Override
    public PageResponse<MyPostPageResponse> getUserPosts(Long memberId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<PostListView> myPostList = postRepository.findByMemberIdAndStatus(pageable, memberId, PostStatus.ACTIVE);

        return PageResponse.of(myPostList.stream().map(MyPostPageResponse::toDto).toList(), myPostList);
    }
}
