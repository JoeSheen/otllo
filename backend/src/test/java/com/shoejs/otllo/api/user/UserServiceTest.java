package com.shoejs.otllo.api.user;

import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private final UUID id = UUID.fromString("c8da08f0-00d8-4730-87e9-19544a0cd72a");

    private Set<User> friends;

    private UserService userService;

    @BeforeEach
    void setUp() {
        friends = new HashSet<>();

        userService = new UserService(userRepository);
    }

    @Test
    void testGetUserDetailsForProfile() {
        when(userRepository.findById(id)).thenReturn(Optional.of(buildUserForTest(true, friends)));

        UserDetailsDto userDetails = userService.getUserDetailsForProfile(id);

        assertUserDetailsDto(userDetails, true, 0);
    }

    @Test
    void testGetUserDetailsForProfileThrowsException() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserDetailsForProfile(id))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage(String.format("User with ID: [%s] not found", id));
    }

    @Test
    void testToggleUserVisibility() {
        when(userRepository.findById(id)).thenReturn(Optional.of(buildUserForTest(true, friends)));
        when(userRepository.save(any(User.class))).thenReturn(buildUserForTest(false, friends));

        UserDetailsDto userDetails = userService.toggleUserVisibility(id, false);

        assertUserDetailsDto(userDetails, false, 0);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testToggleUserVisibilityThrowsException() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.toggleUserVisibility(id, false))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage(String.format("User with ID: [%s] not found", id));
    }

    @Test
    void testAddFriend() {
        UUID friendId = UUID.fromString("25b3157d-6e88-4243-bca7-3fe238d6fe5e");
        User user = buildUserForTest(true, friends);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findById(friendId)).thenReturn(Optional.of(User.builder().id(friendId).build()));
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertThat(user.getFriends()).isEmpty();

        UserDetailsDto userDetails = userService.addFriend(id, friendId);

        assertUserDetailsDto(userDetails, true, 1);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAddFriendThrowsException() {
        UUID friendId = UUID.fromString("25b3157d-6e88-4243-bca7-3fe238d6fe5e");

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));
        when(userRepository.findById(friendId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.addFriend(id, friendId))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage(String.format("User with ID: [%s] not found", friendId));
    }

    @Test
    void testDeleteUserByIdReturnsTrue() {
        when(userRepository.existsById(id)).thenReturn(true);

        userService.deleteUserById(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserByIdReturnsFalse() {
        when(userRepository.existsById(id)).thenReturn(false);

        userService.deleteUserById(id);

        verify(userRepository, times(0)).deleteById(id);

    }

    private void assertUserDetailsDto(UserDetailsDto userDetailsDto, boolean expectedVisibility, int expectedFriendsCount) {
        assertThat(userDetailsDto).isNotNull();
        assertThat(userDetailsDto.id()).isEqualTo(UUID.fromString("c8da08f0-00d8-4730-87e9-19544a0cd72a"));
        assertThat(userDetailsDto.firstName()).isEqualTo("Frank");
        assertThat(userDetailsDto.lastName()).isEqualTo("Long");
        assertThat(userDetailsDto.dateOfBirth()).isEqualTo(LocalDate.of(1985, Month.JUNE, 28));
        assertThat(userDetailsDto.gender()).isEqualTo("MALE");
        assertThat(userDetailsDto.email()).isEqualTo("frank.long@gmail.com");
        assertThat(userDetailsDto.phoneNumber()).isEqualTo("070123456789");
        assertThat(userDetailsDto.username()).isEqualTo("longFrank");
        assertThat(userDetailsDto.profileImage()).isEqualTo("/some/path/");
        assertThat(userDetailsDto.friends().size()).isEqualTo(expectedFriendsCount);
        assertThat(userDetailsDto.visible()).isEqualTo(expectedVisibility);

        verify(userRepository, times(1)).findById(id);
    }

    private User buildUserForTest(boolean visibility, Set<User> friends) {
        final LocalDate dateOfBirth = LocalDate.of(1985, Month.JUNE, 28);

        return User.builder().id(id).firstName("Frank").lastName("Long").dateOfBirth(dateOfBirth).gender(Gender.MALE)
                .email("frank.long@gmail.com").phoneNumber("070123456789").username("longFrank")
                .profileImagePath("/some/path/").friends(friends).visible(visibility).build();
    }
}
