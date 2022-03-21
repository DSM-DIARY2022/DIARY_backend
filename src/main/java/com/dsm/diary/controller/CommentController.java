package com.dsm.diary.controller;

import com.dsm.diary.dto.request.CommentRequest;
import com.dsm.diary.dto.response.CommentResponse;
import com.dsm.diary.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@RequestParam("post-id") Long postId,
                           @RequestBody @Valid CommentRequest commentRequest) {
        commentService.addComment(postId, commentRequest);
    }

    @DeleteMapping
    public void delComment(@RequestParam("post-id") Long postId) {
        commentService.delComment(postId);
    }

    @PatchMapping("/{comment-id}")
    public void modifyComment(@RequestBody @Valid CommentRequest commentRequest,
                              @PathVariable("comment-id") Long commentId) {
        commentService.modifyComment(commentRequest, commentId);
    }

    @GetMapping
    public CommentResponse getComment(@RequestParam("comment-id") Long commentId) {
        return commentService.getComment(commentId);
    }
}
