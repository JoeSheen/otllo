package com.shoejs.otllo.api.user;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for {@link User} class
 */
@Mapper
public interface UserMapper {

    UserMapper INST = Mappers.getMapper(UserMapper.class);

    /**
     * Method for mapping a user object to a {@link UserDetailsDto}
     *
     * @param user class to be mapped
     * @return {@link UserDetailsDto} based on the passed in {@link User} class
     */
    @Mapping(target = "profileImage", source = "profileImagePath")
    UserDetailsDto userToUserDetailsDto(User user);

    /**
     * Method for updating a {@link User} based on the passed in {@link UserUpdateDto}
     *
     * @param user the user class to be updated
     * @param updateDto dto containing the updates for the user class
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateUserFromDto(@MappingTarget User user, UserUpdateDto updateDto);

    @Mapping(target = "profileImage", source = "profileImagePath")
    UserSummaryDto userToUserSummaryDto(User user);
}
