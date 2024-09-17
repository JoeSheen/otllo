package com.shoejs.otllo.api.authentication;

import com.shoejs.otllo.api.user.Gender;
import com.shoejs.otllo.api.user.User;
import com.shoejs.otllo.api.user.UserDetailsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private final AuthenticationMapper mapper = new AuthenticationMapperImpl();

    @Test
    void testSignupCredentialsDtoToUser() {
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        SignupCredentialsDto credentials = new SignupCredentialsDto("Anthony", "McCord",
                LocalDate.of(1991, Month.MAY, 23), "MALE", "anthony.mccord@hotmail.com",
                "070123456789", "Antfeen", "password", "/some/path/", true);
        User user = mapper.signupCredentialsDtoToUser(credentials);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getUpdatedAt()).isNull();
        assertThat(user.getFirstName()).isEqualTo("Anthony");
        assertThat(user.getLastName()).isEqualTo("McCord");
        assertThat(user.getDateOfBirth()).isEqualTo(LocalDate.of(1991, Month.MAY, 23));
        assertThat(user.getGender()).isEqualTo(Gender.MALE);
        assertThat(user.getEmail()).isEqualTo("anthony.mccord@hotmail.com");
        assertThat(user.getPhoneNumber()).isEqualTo("070123456789");
        assertThat(user.getUsername()).isEqualTo("Antfeen");
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        assertThat(user.getProfileImagePath()).isEqualTo("/some/path/");
        assertThat(user.getFriends()).isNull();
        assertThat(user.isVisible()).isTrue();
    }

    @Test
    void testUserToAuthenticationDetailsDto() {
        String jwt = "fake-jwt";
        User user = User.builder().id(UUID.fromString("c32a03c7-a51f-40b7-aa4b-6a8e9417e327"))
                .firstName("Anthony").lastName("McCord").dateOfBirth(LocalDate.of(1991, Month.MAY, 23))
                .gender(Gender.MALE).email("anthony.mccord@hotmail.com").phoneNumber("070123456789").username("Antfeen")
                .profileImagePath("/some/path/").friends(Collections.emptySet()).visible(true).build();

        AuthenticationDetailsDto authDetails = mapper.userToAuthenticationDetailsDto(jwt, user);

        assertThat(authDetails).isNotNull();
        assertThat(authDetails.token()).isEqualTo("fake-jwt");
        UserDetailsDto userDetails = authDetails.userDetailsDto();
        assertThat(userDetails.id()).isEqualTo(UUID.fromString("c32a03c7-a51f-40b7-aa4b-6a8e9417e327"));
        assertThat(userDetails.firstName()).isEqualTo("Anthony");
        assertThat(userDetails.lastName()).isEqualTo("McCord");
        assertThat(userDetails.dateOfBirth()).isEqualTo(LocalDate.of(1991, Month.MAY, 23));
        assertThat(userDetails.gender()).isEqualTo("MALE");
        assertThat(userDetails.email()).isEqualTo("anthony.mccord@hotmail.com");
        assertThat(userDetails.phoneNumber()).isEqualTo("070123456789");
        assertThat(userDetails.username()).isEqualTo("Antfeen");
        assertThat(userDetails.profileImage()).isEqualTo("/some/path/");
        assertThat(userDetails.friends()).isEmpty();
        assertThat(userDetails.visible()).isTrue();
    }
}
