package com.dailyword.follow.application.service;

import com.dailyword.common.domain.PageResponse;
import com.dailyword.follow.adapter.in.dto.GetFollowList;
import com.dailyword.follow.application.port.out.FollowRepositoryPort;
import com.dailyword.follow.application.usecase.GetFollowingListUsecase;
import com.dailyword.follow.domain.model.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.dailyword.follow.domain.constant.FollowStatus.*;

@Service
@RequiredArgsConstructor
public class GetFollowingListService implements GetFollowingListUsecase {

    private final FollowRepositoryPort followRepositoryPort;
    private static final Integer pageSize = 100;

    /**
     * 특정 사용자가 팔로우하고 있는 사용자들의 목록을 페이지네이션으로 조회합니다.
     *
     * 해당 사용자가 팔로우하고 있는 사용자들의 정보를 페이지 단위로 조회합니다.
     * 한 페이지당 20개의 데이터를 반환하며, FOLLOWING 상태의 관계만을 대상으로 합니다.
     *
     * @param memberId 팔로잉 목록을 조회할 사용자의 ID
     * @param page 조회할 페이지 번호 (0부터 시작)
     * @return 팔로잉 목록과 페이지 정보를 포함한 응답
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse<GetFollowList> getFollowList(Long memberId, Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Follow> followPage = followRepositoryPort.findFollowerPage(memberId, FOLLOWING, pageable);
        List<GetFollowList> followeeIdList = followPage.getContent()
            .stream().map(GetFollowList::create)
            .collect(Collectors.toList());

        return PageResponse.of(followeeIdList, followPage);
    }
}
