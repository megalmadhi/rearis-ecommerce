package com.rearis.common;

import org.springframework.data.domain.Page;
import java.util.List;

public record PageResponse<T>(
        List<T> content,       // the items on this page
        int page,              // current page number
        int size,              // items per page
        long totalElements,    // items in total
        int totalPages         // pages in total
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}