package com.shoejs.otllo.api.authentication;

import com.shoejs.otllo.api.exception.DuplicateEntityException;
import com.shoejs.otllo.api.jwt.JwtUtilsService;
import com.shoejs.otllo.api.user.Gender;
import com.shoejs.otllo.api.user.User;
import com.shoejs.otllo.api.user.UserDetailsDto;
import com.shoejs.otllo.api.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtilsService jwtUtilsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private final AuthenticationMapper mapper = new AuthenticationMapperImpl();

    private AuthenticationService authenticationService;

    private final UUID id = UUID.fromString("dbfb3886-a269-40ef-9a15-e43aa2884699");

    private final String firstName = "Bobby";

    private final String lastName = "Spencer";

    private final LocalDate dateOfBirth = LocalDate.of(1999, Month.APRIL, 3);

    private final String gender = "MALE";

    private final String email = "bobbyspencer@gmail.com";

    private final String phoneNumber = "070123456789";

    private final String username = "Bobby99";

    private final String password = "password123";

    private final String imagePath = "/some/path/";

    private final boolean visible = true;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(userRepository, jwtUtilsService, authenticationManager, mapper);
    }

    @Test
    void testSignup() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(buildUserForTest());
        when(jwtUtilsService.generateToken(anyString(), any(UUID.class))).thenReturn("test.jwt");
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        SignupCredentialsDto credentialsDto = new SignupCredentialsDto(firstName, lastName, dateOfBirth, gender,
                email, phoneNumber, username, password, imagePath, visible);

        AuthenticationDetailsDto authDetailsDto = authenticationService.signup(credentialsDto);
        assertAuthenticationDetailsDto(authDetailsDto);
    }

    @Test
    void testSignupThrowsException() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        SignupCredentialsDto credentialsDto = new SignupCredentialsDto(firstName, lastName, dateOfBirth, gender,
                email, phoneNumber, username, password, imagePath, visible);

        assertThatThrownBy(() -> authenticationService.signup(credentialsDto)).
                isInstanceOf(DuplicateEntityException.class).hasMessage("Email already registered");
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testLogin() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(buildUserForTest());
        when(jwtUtilsService.generateToken(anyString(), any(UUID.class))).thenReturn("test.jwt");

        LoginCredentialsDto credentialsDto = new LoginCredentialsDto(username, password);

        AuthenticationDetailsDto authDetailsDto = authenticationService.login(credentialsDto);
        assertAuthenticationDetailsDto(authDetailsDto);
    }

    private void assertAuthenticationDetailsDto(AuthenticationDetailsDto authDetails) {
        assertThat(authDetails).isNotNull();
        assertThat(authDetails.token()).isEqualTo("test.jwt");
        assertUserDetailsDto(authDetails.userDetailsDto());
    }

    private void assertUserDetailsDto(UserDetailsDto userDetailsDto) {
        assertThat(userDetailsDto).isNotNull();
        assertThat(userDetailsDto.id()).isEqualTo(UUID.fromString("dbfb3886-a269-40ef-9a15-e43aa2884699"));
        assertThat(userDetailsDto.firstName()).isEqualTo("Bobby");
        assertThat(userDetailsDto.lastName()).isEqualTo("Spencer");
        assertThat(userDetailsDto.dateOfBirth()).isEqualTo(LocalDate.of(1999, Month.APRIL, 3));
        assertThat(userDetailsDto.gender()).isEqualTo("MALE");
        assertThat(userDetailsDto.email()).isEqualTo("bobbyspencer@gmail.com");
        assertThat(userDetailsDto.phoneNumber()).isEqualTo("070123456789");
        assertThat(userDetailsDto.username()).isEqualTo("Bobby99");
        assertThat(userDetailsDto.profileImage()).isEqualTo("/some/path/");
        assertThat(userDetailsDto.friends()).isEmpty();
        assertThat(userDetailsDto.visible()).isTrue();
    }

    private User buildUserForTest() {
        return User.builder().id(id).firstName(firstName).lastName(lastName).dateOfBirth(dateOfBirth)
                .gender(Gender.MALE).email(email).phoneNumber(phoneNumber).username(username)
                .profileImagePath(imagePath).friends(Collections.emptySet()).visible(visible).build();
    }
}
