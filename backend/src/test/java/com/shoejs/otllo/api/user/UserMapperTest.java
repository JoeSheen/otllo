package com.shoejs.otllo.api.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void testUserToUserDetailsDto() {
        // given
        User user = buildUserForTest();

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
        assertThat(userDetails.friends()).isEmpty();
        assertThat(userDetails.visible()).isTrue();
    }

    private User buildUserForTest() {
        final UUID id = UUID.fromString("e01cc284-933d-42c7-89eb-70411b394cd2");
        final LocalDate dateOfBirth = LocalDate.of(1993, Month.AUGUST, 29);

        return User.builder().id(id).firstName("Mary").lastName("Wright").dateOfBirth(dateOfBirth)
                .gender(Gender.FEMALE).email("mary.wright93@gmail.com").phoneNumber("070123456789")
                .username("Agook1993").profileImagePath("/some/path/").friends(Collections.emptySet())
                .visible(true).build();
    }

}