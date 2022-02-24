package com.dsm.diary.controller;

import com.dsm.diary.dto.request.PostRequest;
import com.dsm.diary.dto.response.PostFeelingResponse;
import com.dsm.diary.dto.response.PostIdResponse;
import com.dsm.diary.dto.response.PostResponse;
import com.dsm.diary.dto.response.PostViweListResponse;
import com.dsm.diary.service.PostService;
import com.dsm.diary.service.ShowPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RequestMapping("/post")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final ShowPostService showPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostIdResponse sevePost(@RequestBody @Valid PostRequest requset) {
        return postService.sevePost(requset);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostIdResponse updatePost(@PathVariable("postId") Long postId,
                                     @RequestBody @Valid PostRequest requset) {
        return postService.updatePost(postId, requset);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse showPost(@PathVariable("postId") Long postId) {
        return showPostService.showPost(postId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostViweListResponse showMyPostList() {
        return showPostService.showMyPostList();
    }

    @GetMapping("/filtering/date")
    @ResponseStatus(HttpStatus.OK)
    public PostViweListResponse showDatePostList(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM") LocalDate date ) {
        return showPostService.showDatePostList(date);
    }

    @GetMapping("/filtering/feeling")
    @ResponseStatus(HttpStatus.OK)
    public PostViweListResponse showDatePostList(@RequestParam(value = "feeling") Integer feeling) {
        return showPostService.showFeelingPostList(feeling);
    }

    @GetMapping("/feeling")
    @ResponseStatus(HttpStatus.OK)
    public PostFeelingResponse showFeelingCount(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM") LocalDate date){
       return showPostService.showFeelingCount(date);
    }

}
