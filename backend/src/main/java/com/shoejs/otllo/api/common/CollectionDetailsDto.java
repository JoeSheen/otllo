package com.shoejs.otllo.api.common;

import java.util.List;

/**
 * Collection class used for returning collections of any details objects
 * @param details the details within the collection
 * @param currentPage the current page
 * @param totalPages the total number of pages
 * @param totalElements the total number of elements
 * @param <T> the type the collection contains
 */
public record CollectionDetailsDto<T>(
        List<T> details,
        int currentPage,
        int totalPages,
        long totalElements
) {}
