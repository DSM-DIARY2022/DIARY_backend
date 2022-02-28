package com.dsm.diary.service;

import com.dsm.diary.Facade.CommentFacade;
import com.dsm.diary.dto.request.CommentRequest;
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
}
