package com.dsm.diary.Facade;

import com.dsm.diary.Exception.NotFoundException;
import com.dsm.diary.dto.request.CommentRequest;
import com.dsm.diary.entity.comments.Comment;
import com.dsm.diary.entity.comments.CommentRepository;
import com.dsm.diary.entity.post.Post;
import com.dsm.diary.entity.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFacade {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public void addComment(Long postId, CommentRequest commentRequest){
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundException::new);

        commentRepository.save(
                new Comment(
                        commentRequest.getComment(),
                        post
                )
        );
    }

    public void delComment(Long commentId){
        commentRepository.delete(getById(commentId));
    }

    public void modifyComment(CommentRequest commentRequest, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .map(newComment -> newComment.updateComment(
                        commentRequest.getComment()
                ))
                .orElseThrow(NotFoundException::new);
    }

    public Comment getById(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(NotFoundException::new);
    }
}
