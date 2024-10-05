package com.shoejs.otllo.api.authentication;

import com.shoejs.otllo.api.user.User;
import com.shoejs.otllo.api.user.UserDetailsDto;
import com.shoejs.otllo.api.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class AuthenticationMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    public abstract User signupCredentialsDtoToUser(SignupCredentialsDto credentialsDto);

    @Mapping(target = "token", source = "jwt")
    @Mapping(target = "userDetailsDto", expression = "java(convertUserToUserDetailsDto(user))")
    public abstract AuthenticationDetailsDto userToAuthenticationDetailsDto(String jwt, User user);

    protected UserDetailsDto convertUserToUserDetailsDto(User user) {
        return UserMapper.INST.userToUserDetailsDto(user);
    }

    @Named("encodePassword")
    protected String encodeUserPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
