package com.shoejs.otllo.api.user;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

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

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll(ArgumentMatchers.<Specification<User>>any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(buildUserForTest("frank.long@gmail.com", "070123456789", true))));

        CollectionDetailsDto<UserDetailsDto> collectionDetailsDto = userService.getAllUsers("Frank", 0, 50);

        assertThat(collectionDetailsDto).isNotNull();
        assertThat(collectionDetailsDto.details().size()).isEqualTo(1);
        assertUserDetailsDto(collectionDetailsDto.details().get(0), "frank.long@gmail.com", "070123456789", true);
        assertThat(collectionDetailsDto.currentPage()).isEqualTo(0);
        assertThat(collectionDetailsDto.totalPages()).isEqualTo(1);
        assertThat(collectionDetailsDto.totalElements()).isEqualTo(1L);
    }

    @Test
    void testGetUserDetailsForProfile() {
        when(userRepository.findById(id)).thenReturn(Optional.of(buildUserForTest("frank.long@gmail.com", "070123456789", true)));

        UserDetailsDto userDetails = userService.getUserDetailsForProfile(id);

        assertUserDetailsDto(userDetails, "frank.long@gmail.com", "070123456789", true);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testGetUserDetailsForProfileThrowsException() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserDetailsForProfile(id))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage(String.format("User with ID: [%s] not found", id));
    }

    @Test
    void testToggleUserVisibility() {
        when(userRepository.findById(id)).thenReturn(Optional.of(buildUserForTest("frank.long@gmail.com", "070123456789", true)));
        when(userRepository.save(any(User.class))).thenReturn(buildUserForTest("frank.long@gmail.com", "070123456789", false));

        UserDetailsDto userDetails = userService.toggleUserVisibility(id, false);

        assertUserDetailsDto(userDetails, "frank.long@gmail.com", "070123456789", false);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testToggleUserVisibilityThrowsException() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.toggleUserVisibility(id, false))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage(String.format("User with ID: [%s] not found", id));
    }

    @Test
    void testUpdateUserProfile() {
        when(userRepository.findById(id)).thenReturn(Optional.of(buildUserForTest("frank.long@gmail.com", "070123456789", true)));
        when(userRepository.save(any(User.class))).thenReturn(buildUserForTest("frank.long85@outlook.co.uk", "079876543210", true));

        UserUpdateDto updateDto = new UserUpdateDto("Frank", "Long", "frank.long85@outlook.co.uk",
                "079876543210", "some status value");

        UserDetailsDto userDetails = userService.updateUserProfile(id, updateDto);
        assertUserDetailsDto(userDetails, "frank.long85@outlook.co.uk", "079876543210", true);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUserProfileThrowsException() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserUpdateDto updateDto = new UserUpdateDto("Frank", "Long", "frank.long@gmail.com",
                "070123456789", "some status value");
        assertThatThrownBy(() -> userService.updateUserProfile(id, updateDto))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage(String.format("User with ID: [%s] not found", id));
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

    private void assertUserDetailsDto(UserDetailsDto userDetailsDto, String expectedEmail, String expectedPhoneNumber, boolean expectedVisibility) {
        assertThat(userDetailsDto).isNotNull();
        assertThat(userDetailsDto.id()).isEqualTo(UUID.fromString("c8da08f0-00d8-4730-87e9-19544a0cd72a"));
        assertThat(userDetailsDto.firstName()).isEqualTo("Frank");
        assertThat(userDetailsDto.lastName()).isEqualTo("Long");
        assertThat(userDetailsDto.dateOfBirth()).isEqualTo(LocalDate.of(1985, Month.JUNE, 28));
        assertThat(userDetailsDto.gender()).isEqualTo("MALE");
        assertThat(userDetailsDto.email()).isEqualTo(expectedEmail);
        assertThat(userDetailsDto.phoneNumber()).isEqualTo(expectedPhoneNumber);
        assertThat(userDetailsDto.username()).isEqualTo("longFrank");
        assertThat(userDetailsDto.profileImage()).isEqualTo("/some/path/");
        assertThat(userDetailsDto.visible()).isEqualTo(expectedVisibility);
        assertThat(userDetailsDto.status()).isEqualTo("some status value");
    }

    private User buildUserForTest(String email, String phoneNumber, boolean visibility) {
        final LocalDate dateOfBirth = LocalDate.of(1985, Month.JUNE, 28);

        return User.builder().id(id).firstName("Frank").lastName("Long").dateOfBirth(dateOfBirth).gender(Gender.MALE)
                .email(email).phoneNumber(phoneNumber).username("longFrank").profileImagePath("/some/path/")
                .visible(visibility).status("some status value").build();
    }
}
