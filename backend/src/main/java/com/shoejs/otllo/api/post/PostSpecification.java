package com.shoejs.otllo.api.post;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification class for filtering and searching for Posts
 */
public class PostSpecification {

    /**
     * Method for filtering posts by author username or title
     *
     * @param searchValue value that must be matched inorder to be returned
     * @return {@link Specification} for returning posts with a specific title or author
     */
    public static Specification<Post> filterPostsBySearchValue(String searchValue) {
        return (root, query, builder) -> {
            Predicate usernamePredicate = builder.like(builder.upper(root.get("user").get("username")), "%" + searchValue + "%");
            Predicate titlePredicate = builder.like(builder.upper(root.get("title")), "%" + searchValue + "%");
            return builder.or(usernamePredicate, titlePredicate);
        };
    }
}
