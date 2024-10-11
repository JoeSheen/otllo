package com.shoejs.otllo.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for performing CRUD operations on user objects
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Method for checking if a user with the provided email is already registered
     *
     * @param email to be checked
     * @return true if the email is found within the user table otherwise false
     */
    boolean existsByEmail(String email);

    /**
     * Method for getting a user via their username
     *
     * @param username of the user being looked for
     * @return an {@link Optional} with a present value if the user is found, otherwise an empty Optional
     */
    Optional<User> findByUsername(String username);
}
