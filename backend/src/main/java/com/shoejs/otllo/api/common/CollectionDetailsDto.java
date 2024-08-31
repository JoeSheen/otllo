package com.shoejs.otllo.api.common;

import java.util.List;

public record CollectionDetailsDto<T>(
        List<T> details,
        int currentPage,
        int totalPages,
        long totalElements
) {}
