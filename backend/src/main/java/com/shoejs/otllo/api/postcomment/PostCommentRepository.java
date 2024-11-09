package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for performing CRUD operations on post comments
 */
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, UUID> {

    Page<PostComment> findByPost(Post post, Pageable pageable);
}
