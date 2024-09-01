package com.shoejs.otllo.api.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INST = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "profileImage", source = "profileImagePath")
    UserDetailsDto userToUserDetailsDto(User user);
}
