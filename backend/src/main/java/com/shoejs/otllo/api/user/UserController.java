package com.shoejs.otllo.api.user;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for User entities
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint for returning a collection of users that match a given search value
     *
     * @param searchValue the search value the users must returned must fulfil
     * @param pageNumber number of the page being requested (default 0)
     * @param pageSize size of the page being requested (default 150)
     * @return collection of users that contain that match the provided search value
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionDetailsDto<UserSummaryDto> getAllUsers(
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "150") Integer pageSize) {
        return userService.getAllUsers(searchValue.toUpperCase(), pageNumber, pageSize);
    }

    /**
     * Method for returning user details for the passed in id
     *
     * @param id of the user
     * @return user details
     */
    @GetMapping("/{id}/profile")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto getUserDetailsForProfile(@PathVariable("id") UUID id) {
        return userService.getUserDetailsForProfile(id);
    }

    /**
     * This method is for toggling the visibility flag for a user
     *
     * @param id of the user
     * @param visibility new value for the visible flag
     * @return user details with update visibility flag
     */
    @PatchMapping("/togglevisibility/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto toggleUserVisibility(@PathVariable("id") UUID id, @RequestParam(defaultValue = "true") Boolean visibility) {
        return userService.toggleUserVisibility(id, visibility);
    }

    /**
     * Method allowing a user to update their details
     *
     * @param id of the user
     * @param updateDto the updated user details
     * @return updated user details Dto
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto updateUserProfile(@PathVariable("id") UUID id, @Valid @RequestBody UserUpdateDto updateDto) {
        return userService.updateUserProfile(id, updateDto);
    }

    /**
     * Method for deleting a user via their id
     *
     * @param id of the user
     * @return ok response if the user was deleted otherwise returns not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") UUID id) {
        if (userService.deleteUserById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
