package com.shoejs.otllo.api.postcomment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostCommentMapper {

    PostCommentMapper INST = Mappers.getMapper(PostCommentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "post", ignore = true)
    PostComment postCommentCreateUpdateDtoToPostComment(PostCommentCreateUpdateDto createDto);

    PostCommentDetailsDto postCommentToPostCommentDetailsDto(PostComment postComment);

    void updatePostCommentFromDto(@MappingTarget PostComment postComment, PostCommentCreateUpdateDto updateDto);
}
