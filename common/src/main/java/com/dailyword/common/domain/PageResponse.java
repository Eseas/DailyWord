package com.dailyword.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

    private final List<T> content;
    private final int currentPage;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;
    private final boolean isFirst;
    private final boolean isLast;

    // Page 객체로부터 생성하는 팩토리 메서드
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber() + 1,  // 0-based -> 1-based
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious(),
                page.isFirst(),
                page.isLast()
        );
    }

    // 데이터만 변환하는 팩토리 메서드
    public static <T> PageResponse<T> of(List<T> content, Page<?> pageInfo) {
        return new PageResponse<>(
                content,
                pageInfo.getNumber() + 1,
                pageInfo.getSize(),
                pageInfo.getTotalElements(),
                pageInfo.getTotalPages(),
                pageInfo.hasNext(),
                pageInfo.hasPrevious(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }

    // 타입 변환
    public <U> PageResponse<U> map(Function<T, U> mapper) {
        List<U> mappedContent = content.stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new PageResponse<>(
                mappedContent,
                currentPage,
                pageSize,
                totalElements,
                totalPages,
                hasNext,
                hasPrevious,
                isFirst,
                isLast
        );
    }
}