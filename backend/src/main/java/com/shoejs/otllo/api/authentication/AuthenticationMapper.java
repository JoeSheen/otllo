package com.shoejs.otllo.api.authentication;

import com.shoejs.otllo.api.user.User;
import com.shoejs.otllo.api.user.UserDetailsDto;
import com.shoejs.otllo.api.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Mapper used during the authentication process
 */
@Mapper(componentModel = "spring")
public abstract class AuthenticationMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Method used to map a signup credentials Dto into a User object
     *
     * @param credentialsDto the credentials to be mapped
     * @return User object based on the credentials passed into the method
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "posts", ignore = true)
    public abstract User signupCredentialsDtoToUser(SignupCredentialsDto credentialsDto);

    /**
     * Method for mapping user and token into a single response
     *
     * @param jwt the token for the user
     * @param user object representing recently authenticated user
     * @return single object containing user details and JSON Web Token
     */
    @Mapping(target = "token", source = "jwt")
    @Mapping(target = "userDetailsDto", expression = "java(convertUserToUserDetailsDto(user))")
    public abstract AuthenticationDetailsDto userToAuthenticationDetailsDto(String jwt, User user);

    /**
     * Method used to call the mapper required to convert user objects to user details
     *
     * @param user object to be converted
     * @return converted userDetailsDto object
     */
    protected UserDetailsDto convertUserToUserDetailsDto(User user) {
        return UserMapper.INST.userToUserDetailsDto(user);
    }

    /**
     * Protected method used to encode user passwords
     *
     * @param password plain text password to be encoded
     * @return encoded password
     */
    @Named("encodePassword")
    protected String encodeUserPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
