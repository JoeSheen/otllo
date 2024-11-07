package com.shoejs.otllo.api.postcomment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    private final PostCommentMapper mapper = PostCommentMapper.INST;
}
