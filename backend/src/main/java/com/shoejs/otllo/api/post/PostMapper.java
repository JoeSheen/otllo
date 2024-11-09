package com.shoejs.otllo.api.post;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for {@link Post} class
 */
@Mapper
public interface PostMapper {

    PostMapper INST = Mappers.getMapper(PostMapper.class);

    /**
     * Method declaration for converting {@link PostCreateUpdateDto} into a {@link Post}
     *
     * @param createDto to be converted into a post object
     * @return {@link Post} object based on the passed in {@link PostCreateUpdateDto} object
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Post postCreateUpdateDtoToPost(PostCreateUpdateDto createDto);

    /**
     * Method declaration for converting a {@link Post} into a {@link PostDetailsDto}
     *
     * @param post the post to be converted
     * @return {@link PostDetailsDto} based on the passed in {@link Post} object
     */
    @Mapping(target = "author", expression = "java(post.getUser().getUsername())")
    PostDetailsDto postToPostDetailsDto(Post post);

    /**
     * Method declaration for updating an existing {@link Post} object
     *
     * @param post to be updated
     * @param updateDto new details for the post
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updatePostFromDto(@MappingTarget Post post, PostCreateUpdateDto updateDto);
}
