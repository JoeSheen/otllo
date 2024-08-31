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

    public UserDetailsDto toggleUserVisibility(UUID id, boolean visibility) {
        User user = findUserOrThrow(id);
        user.setVisible(visibility);
        return mapper.userToUserDetailsDto(userRepository.save(user));
    }

    public UserDetailsDto addFriend(UUID userId, UUID friendId) {
        // NOTE - temp version of this method for now. Proper version will be
        // added after auth work has been done.
        User user = findUserOrThrow(userId);
        User friendUser = findUserOrThrow(friendId);
        user.getFriends().add(friendUser);
        return mapper.userToUserDetailsDto(userRepository.save(user));
    }

    private User findUserOrThrow(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with ID: [%s] not found", id)));
    }
}
