package com.shoejs.otllo.api.user;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification class for filtering and searching for users
 */
public class UserSpecification {

    /**
     * Method for filtering users by first name, last name, or username
     * @param searchValue value that must be matched inorder to be returned
     * @return {@link Specification} for returning users that match the criteria of the search value
     */
    public static Specification<User> filterUsersBySearchValue(String searchValue) {
        return (root, query, builder) -> {
            Predicate usernamePredicate = builder.like(builder.upper(root.get("username")), "%" + searchValue + "%");
            Predicate firstNamePredicate = builder.like(builder.upper(root.get("firstName")), "%" + searchValue + "%");
            Predicate lastNamePredicate = builder.like(builder.upper(root.get("lastName")), "%" + searchValue + "%");
            return builder.or(usernamePredicate, firstNamePredicate, lastNamePredicate);
        };
    }
}
