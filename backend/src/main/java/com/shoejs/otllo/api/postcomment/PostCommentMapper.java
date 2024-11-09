package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.post.Post;
import com.shoejs.otllo.api.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostCommentMapper {

    PostCommentMapper INST = Mappers.getMapper(PostCommentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    PostComment postCommentCreateDtoToPostComment(PostCommentCreateDto createDto, Post post, User user);

    PostCommentDetailsDto postCommentToPostCommentDetailsDto(PostComment postComment);
}
