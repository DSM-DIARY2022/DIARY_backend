package com.dsm.diary.service;

import com.dsm.diary.Facade.CommentFacade;
import com.dsm.diary.dto.request.CommentRequest;
import com.dsm.diary.dto.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentFacade commentFacade;

    @Transactional
    public void addComment(Long postId, CommentRequest commentRequest){
        commentFacade.addComment(postId, commentRequest);
    }

    @Transactional
    public void delComment(Long commentId){
        commentFacade.delComment(commentId);
    }

    @Transactional
    public void modifyComment(CommentRequest commentRequest, Long commentId){
        commentFacade.modifyComment(commentRequest, commentId);
    }

    @Transactional
    public CommentResponse getComment(Long commentId){
        return commentFacade.getComment(commentId);
    }

    /**
     *
     * 댓글 추가 : 성공하면 201
     *            Post를 찾지 못하면 404
     * 댓글 삭제 : 성공하면 200
     *            삭제할 댓글을 찾지 못하면 404
     * 댓글 수정 : 성공하면 200
     *            수정할 댓글을 찾지 못하면 404
     *
     **/
}
