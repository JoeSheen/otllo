package com.shoejs.otllo.api.user;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INST = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "profileImage", source = "profileImagePath")
    UserDetailsDto userToUserDetailsDto(User user);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateUserFromDto(@MappingTarget User user, UserUpdateDto updateDto);
}
