package com.shoejs.otllo.api.user;

import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service for exposing actions on User objects
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper = UserMapper.INST;

    /**
     * Method for returning user details for the passed in id
     *
     * @param id of the user being requested from the database
     * @return details for the user found
     */
    public UserDetailsDto getUserDetailsForProfile(UUID id) {
        User user = findUserOrThrow(id);
        return mapper.userToUserDetailsDto(user);
    }

    /**
     * Method for toggling a users visibility flag
     *
     * @param id of the user object being changed
     * @param visibility the new value of the visibility field
     * @return user details with update visibility flag
     */
    public UserDetailsDto toggleUserVisibility(UUID id, boolean visibility) {
        User user = findUserOrThrow(id);
        user.setVisible(visibility);
        return mapper.userToUserDetailsDto(userRepository.save(user));
    }

    /**
     * Method allowing a user to update their details
     *
     * @param id of the user object being updated
     * @param updateDto the updated user details
     * @return updated user details Dto
     */
    public UserDetailsDto updateUserProfile(UUID id, UserUpdateDto updateDto) {
        User user = findUserOrThrow(id);
        mapper.updateUserFromDto(user, updateDto);
        return mapper.userToUserDetailsDto(userRepository.save(user));
    }

    /**
     * Method for deleting a user via their id
     *
     * @param id of the user being deleted
     * @return true if the user was deleted, otherwise return false
     */
    public boolean deleteUserById(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * private method for getting a user from the database via the passed in id
     *
     * @param id of the user being searched for
     * @return the user associated with the id parameter
     */
    private User findUserOrThrow(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with ID: [%s] not found", id)));
    }
}
