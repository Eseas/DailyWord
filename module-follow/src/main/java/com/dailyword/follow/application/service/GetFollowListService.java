package com.dailyword.follow.application.service;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.follow.adapter.in.dto.GetFollowList;
import com.dailyword.follow.application.usecase.GetFollowListUsecase;
import com.dailyword.follow.domain.constant.FollowStatus;
import com.dailyword.follow.domain.model.Follow;
import com.dailyword.follow.infrastructure.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.dailyword.follow.domain.constant.FollowStatus.*;

@Service
@RequiredArgsConstructor
public class GetFollowListService implements GetFollowListUsecase {

    private final FollowRepository followRepository;
    private static final Integer pageSize = 20;

    @Override
    public PageResponse<GetFollowList> getFollowList(Long memberId, Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Follow> followPage = followRepository.findFollowPageByfolloweeId(memberId, FOLLOWING, pageable);
        List<GetFollowList> followeeIdList = followPage.getContent().stream().map(GetFollowList::create).collect(Collectors.toList());

        return PageResponse.of(followeeIdList, followPage);
    }
}
