package com.shoejs.otllo.api.post;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INST = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Post postCreateUpdateDtoToPost(PostCreateUpdateDto createDto);

    @Mapping(target = "author", expression = "java(post.getUser().getUsername())")
    PostDetailsDto postToPostDetailsDto(Post post);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updatePostFromDto(@MappingTarget Post post, PostCreateUpdateDto updateDto);
}
