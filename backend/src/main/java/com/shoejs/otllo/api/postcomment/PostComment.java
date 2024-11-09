package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.common.AbsBaseEntity;
import com.shoejs.otllo.api.post.Post;
import com.shoejs.otllo.api.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostComment extends AbsBaseEntity {

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
