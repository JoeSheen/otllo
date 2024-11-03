package com.shoejs.otllo.api.post;

import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    public static Specification<Post> filterByAuthorUsername(String username) {
        return (root, query, builder) -> builder.like(builder.upper(root.get("user").get("username")), username);
    }

    public static Specification<Post> filterByTitle(String title) {
        return (root, query, builder) -> builder.like(builder.upper(root.get("title")), title);
    }
}
