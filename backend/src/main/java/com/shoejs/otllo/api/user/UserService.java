package com.shoejs.otllo.api.user;

import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper = UserMapper.INST;

    public UserDetailsDto getUserDetailsForProfile(UUID id) {
        User user = findUserOrThrow(id);
        return mapper.userToUserDetailsDto(user);
    }

    public UserDetailsDto toggleUserVisibility(UUID id, Boolean visibility) {
        User user = findUserOrThrow(id);
        user.setVisible(visibility);
        return mapper.userToUserDetailsDto(userRepository.save(user));
    }

    public UserDetailsDto addFriend(UUID userId, UUID friendId) {
        // TODO:
        //  NOTE - temp version of this method for now. Proper version will be
        //  added after auth work has been done.
        User user = findUserOrThrow(userId);
        User friendUser = findUserOrThrow(friendId);
        user.getFriends().add(friendUser);
        return mapper.userToUserDetailsDto(userRepository.save(user));
    }

    public boolean deleteUserById(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private User findUserOrThrow(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with ID: [%s] not found", id)));
    }
}
