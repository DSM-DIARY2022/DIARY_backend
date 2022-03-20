package com.dsm.diary.facade;

import com.dsm.diary.entity.comments.Comment;
import com.dsm.diary.entity.comments.CommentRepository;
import com.dsm.diary.entity.post.Post;
import com.dsm.diary.entity.post.PostRepository;
import com.dsm.diary.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment getByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(NotFoundException::new);
    }

    public Post getByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NotFoundException::new);
    }
}
