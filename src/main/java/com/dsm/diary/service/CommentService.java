package com.dsm.diary.service;

import com.dsm.diary.dto.request.CommentRequest;
import com.dsm.diary.dto.response.CommentResponse;
import com.dsm.diary.entity.comments.Comment;
import com.dsm.diary.entity.comments.CommentRepository;
import com.dsm.diary.entity.post.Post;
import com.dsm.diary.exception.NotFoundException;
import com.dsm.diary.facade.CommentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentFacade commentFacade;
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(Long postId, CommentRequest commentRequest) {
        Post post = commentFacade.getByPostId(postId);

        commentRepository.save(
                new Comment(
                        commentRequest.getContent(),
                        post
                )
        );
    }

    @Transactional
    public void delComment(Long commentId) {
        commentRepository.delete(commentFacade.getByCommentId(commentId));
    }

    @Transactional
    public void modifyComment(CommentRequest commentRequest, Long commentId) {
        commentRepository.findById(commentId)
                .map(newComment -> newComment.updateComment(
                        commentRequest.getContent()
                ))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public CommentResponse getComment(Long id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    CommentResponse response = CommentResponse.builder()
                            .commentId(id)
                            .content(comment.getContent())
                            .build();
                    return response;
                })
                .orElseThrow(NotFoundException::new);
    }
    /**
     *
     * ?????? ?????? : ???????????? 201
     *            post??? ?????? ????????? 404
     * ?????? ?????? : ???????????? 200
     *            ????????? ????????? ?????? ????????? 404
     * ?????? ?????? : ???????????? 200
     *            ????????? ????????? ?????? ????????? 404
     *
     **/
}
