package com.shoejs.otllo.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}/profile")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody UserDetailsDto getUserDetailsForProfile(@PathVariable("id") UUID id) {
        return userService.getUserDetailsForProfile(id);
    }

    @PatchMapping("/togglevisibility/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody UserDetailsDto toggleUserVisibility(@PathVariable("id") UUID id, @RequestParam(defaultValue = "true") Boolean visibility) {
        return userService.toggleUserVisibility(id, visibility);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody UserDetailsDto updateUserProfile(@PathVariable("id") UUID id, @Valid @RequestBody UserUpdateDto updateDto) {
        return userService.updateUserProfile(id, updateDto);
    }

    /*@PutMapping("/addfriend/{id}/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody UserDetailsDto addFriendToUser(UUID id, UUID friendId) {
        // TODO: this will change in future task.
        throw new RuntimeException("method not implemented atm, will be done in future");
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") UUID id) {
        if (userService.deleteUserById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
