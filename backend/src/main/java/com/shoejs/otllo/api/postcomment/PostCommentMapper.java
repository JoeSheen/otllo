package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.post.Post;
import com.shoejs.otllo.api.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for {@link PostComment} class
 */
@Mapper
public interface PostCommentMapper {

    PostCommentMapper INST = Mappers.getMapper(PostCommentMapper.class);

    /**
     * Method declaration for converting {@link PostCommentCreateDto} to {@link PostComment}
     *
     * @param createDto data to be converted into post comment
     * @param post the post the comment is associated with
     * @param user the user creating the post comment
     * @return {@link PostComment} based off the passed in values
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    PostComment postCommentCreateDtoToPostComment(PostCommentCreateDto createDto, Post post, User user);

    /**
     * Method declaration for converting a {@link PostComment} into a {@link PostCommentDetailsDto}
     *
     * @param postComment the post comment to be converted
     * @return {@link PostCommentDetailsDto} based on the passed in {@link PostComment}
     */
    @Mapping(target = "author", expression = "java(postComment.getUser().getUsername())")
    PostCommentDetailsDto postCommentToPostCommentDetailsDto(PostComment postComment);
}
