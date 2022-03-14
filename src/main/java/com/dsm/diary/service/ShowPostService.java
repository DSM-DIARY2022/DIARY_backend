package com.dsm.diary.service;

import com.dsm.diary.Exception.NotFoundException;
import com.dsm.diary.dto.response.PostFeelingResponse;
import com.dsm.diary.dto.response.PostResponse;
import com.dsm.diary.dto.response.PostViweListResponse;
import com.dsm.diary.dto.response.PostViweResponse;
import com.dsm.diary.entity.post.Post;
import com.dsm.diary.entity.post.PostRepository;
import com.dsm.diary.service.Util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShowPostService {

    private final AuthUtil authUtil;
    private final PostRepository postRepository;

    /**
    *
    * 게시글 상세보기
    * 성공시 200과 PostResponse 반환, 실패시 404(일기를 찾지 못함),403(자신의 일기가 아님)
    *
    * author : chlgml
    **/
    public PostResponse showPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException("게시글을 찾을 수 없습니다."));

        authUtil.verificationAccount(post.getAccount());

        return PostResponse.builder()
                .id(post.getId())
                .feeling(post.getFeeling())
                .title(post.getTitle())
                .content(post.getContent())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .comment(post.getComment())
                .build();
    }


    /**
     *
     * 일기 목록을 보여줌
     * 성공시 200과 PostViweListResponse 반환, 실패시 404(일기를 찾지 못함)
     *
     * author : chlgml
     **/
    public PostViweListResponse showMyPostList() {
        List<Post> posts = postRepository.findAllByAccountOrderByCreatedDateDesc(authUtil.getAccount());
        return getPostList(posts);
    }

    /**
     *
     * 입력받은 기분에 맞는 일기 목록을 보여줌
     * 성공시 200과 PostViweListResponse 반환, 실패시 404(일기를 찾지 못함)
     *
     * author : chlgml
     **/
    public PostViweListResponse showFeelingPostList(Integer feeling) {
        List<Post> posts = postRepository.findAllByAccountAndFeelingOrderByCreatedDateDesc(authUtil.getAccount(), feeling);
        return getPostList(posts);
    }

    /**
    *
    * 년도와 월을 입력받아 년도와 월에 맞는 일기목록을 보여줌
    * 성공시 200과 PostViweListResponse 반환, 실패시 404(일기를 찾지 못함)
    *
    * author : chlgml
    **/
    public PostViweListResponse showDatePostList(LocalDate date) {
        List<Post> posts = postRepository.findAllByCreatedDateOrderByCreatedDateDesc(date);
        return getPostList(posts);
    }

    /**
    *
    * 입력받은 월 일기의 기분 별 개수
    * 성공시 200과 PostFeelingResponse 반환, 실패시 404(일기를 찾지 못함)
    *
    * author : chlgml
    **/
    public PostFeelingResponse showFeelingCount(LocalDate date) {
        List<Post> posts = postRepository.findAllByCreatedDate(date);
        Integer feelingCount = 0;
        for(Post post : posts) {
            feelingCount += post.getFeeling();
        }
        return new PostFeelingResponse(feelingCount);
    }

    private PostViweListResponse getPostList(List<Post> posts) {
        List<PostViweResponse> postViweResponses = new ArrayList<>();
        for(Post post : posts) {
            postViweResponses.add(
                    PostViweResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .createdDate(post.getCreatedDate())
                            .modifiedDate(post.getModifiedDate())
                            .build()
            );
        }
        return new PostViweListResponse(postViweResponses);
    }

}
