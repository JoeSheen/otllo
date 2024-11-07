package com.shoejs.otllo.api.postcomment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for performing CRUD operations on post comments
 */
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, UUID> {
}
