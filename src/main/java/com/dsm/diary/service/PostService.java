package com.dsm.diary.service;

import com.dsm.diary.Exception.BadRequestException;
import com.dsm.diary.Exception.NotFoundException;
import com.dsm.diary.dto.request.PostRequest;
import com.dsm.diary.dto.response.PostIdResponse;
import com.dsm.diary.entity.post.Post;
import com.dsm.diary.entity.post.PostRepository;
import com.dsm.diary.service.Util.AuthUtiil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final AuthUtiil authUtiil;
    private final PostRepository postRepository;

    /**
    *
    * 일기를 저장
    * 성공시 201과 일기 id값 반환, 실패시 404(일기를 찾지 못함), 400(기분 필드의 값이 올바르지 않음)
    *
    * author : chlgml
    **/
    public PostIdResponse sevePost(PostRequest requset) {
        checkFeeling(requset.getFeeling());
        Long id = postRepository.save(
                Post.builder()
                        .account(authUtiil.getAccount())
                        .title(requset.getTitle())
                        .content(requset.getContent())
                        .feeling(requset.getFeeling())
                        .build()
        ).getId();
        return new PostIdResponse(id);
    }

    /**
    *
    * 일기를 수정
    * 성공시 201과 일기 id값 반환, 실패시 404(일기를 찾지 못함), 400(기분 필드의 값이 올바르지 않음)
    *
    * author : chlgml
    **/
    public PostIdResponse updatePost(Long postId, PostRequest requset) {
        checkFeeling(requset.getFeeling());

        Post post = postRepository.findById(postId)
                .orElseThrow(()->new NotFoundException("게시글을 찾을 수 없습니다."));

        authUtiil.verificationAccount(post.getAccount());

        post.update(requset);
        postRepository.save(post);
        return new PostIdResponse(post.getId());
    }

    private void checkFeeling(Integer feeling) {
        if(feeling < 0 || feeling > 5) {
            throw new BadRequestException("별의 개수는 1이상 5이하이여야 합니다.");
        }
    }

}