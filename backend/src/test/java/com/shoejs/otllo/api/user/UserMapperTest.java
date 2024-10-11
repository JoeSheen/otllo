package com.shoejs.otllo.api.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void testUserToUserDetailsDto() {
        // given
        User user = buildUserForTest("Mary", "Wright", "mary.wright93@gmail.com", "070123456789");

        // when
        UserDetailsDto userDetails = UserMapper.INST.userToUserDetailsDto(user);

        // then
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.id()).isEqualTo(UUID.fromString("e01cc284-933d-42c7-89eb-70411b394cd2"));
        assertThat(userDetails.firstName()).isEqualTo("Mary");
        assertThat(userDetails.lastName()).isEqualTo("Wright");
        assertThat(userDetails.dateOfBirth()).isEqualTo(LocalDate.of(1993, Month.AUGUST, 29));
        assertThat(userDetails.gender()).isEqualTo("FEMALE");
        assertThat(userDetails.email()).isEqualTo("mary.wright93@gmail.com");
        assertThat(userDetails.phoneNumber()).isEqualTo("070123456789");
        assertThat(userDetails.username()).isEqualTo("Agook1993");
        assertThat(userDetails.profileImage()).isEqualTo("/some/path/");
        assertThat(userDetails.visible()).isTrue();
        assertThat(userDetails.status()).isEqualTo("some status value");
    }

    @Test
    void testUpdateUserFromDto() {
        User user = buildUserForTest("mary", "wright", "mary_wright1993@outlook.co.uk", "079876543210");
        UserUpdateDto updateDto = new UserUpdateDto("Mary", "Wright", "mary.wright93@gmail.com",
                "070123456789", "some status value");

        UserMapper.INST.updateUserFromDto(user, updateDto);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(UUID.fromString("e01cc284-933d-42c7-89eb-70411b394cd2"));
        assertThat(user.getFirstName()).isEqualTo("Mary");
        assertThat(user.getLastName()).isEqualTo("Wright");
        assertThat(user.getDateOfBirth()).isEqualTo(LocalDate.of(1993, Month.AUGUST, 29));
        assertThat(user.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("mary.wright93@gmail.com");
        assertThat(user.getPhoneNumber()).isEqualTo("070123456789");
        assertThat(user.getUsername()).isEqualTo("Agook1993");
        assertThat(user.getProfileImagePath()).isEqualTo("/some/path/");
        assertThat(user.isVisible()).isTrue();
        assertThat(user.getStatus()).isEqualTo("some status value");
    }

    private User buildUserForTest(String firstName, String lastName, String email, String phoneNumber) {
        final UUID id = UUID.fromString("e01cc284-933d-42c7-89eb-70411b394cd2");
        final LocalDate dateOfBirth = LocalDate.of(1993, Month.AUGUST, 29);

        return User.builder().id(id).firstName(firstName).lastName(lastName).dateOfBirth(dateOfBirth)
                .gender(Gender.FEMALE).email(email).phoneNumber(phoneNumber)
                .username("Agook1993").profileImagePath("/some/path/")
                .visible(true).status("some status value").build();
    }
}
