package com.shoejs.otllo.api.user;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
     * Method for returning a collection of users that match a given search value
     *
     * @param searchValue the search value the user must first name, last name or username must contain
     * @param pageNumber number of the page being requested
     * @param pageSize size of the page being requested
     * @return collection of users that contain the requested value
     */
    public CollectionDetailsDto<UserDetailsDto> getAllUsers(String searchValue, int pageNumber, int pageSize) {
        Specification<User> spec = Specification.where(UserSpecification.filterUsersBySearchValue(searchValue));
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable paging = PageRequest.of(pageNumber, pageSize, sort);
        Page<UserDetailsDto> page = userRepository.findAll(spec, paging).map(mapper::userToUserDetailsDto);
        return new CollectionDetailsDto<>(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

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
