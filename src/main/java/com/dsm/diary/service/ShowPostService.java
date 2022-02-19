package com.dsm.diary.service;

import com.dsm.diary.Exception.ForbiddenException;
import com.dsm.diary.Exception.NotFoundException;
import com.dsm.diary.dto.response.PostResponse;
import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.post.Post;
import com.dsm.diary.entity.post.PostRepository;
import com.dsm.diary.service.Util.AuthUtiil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShowPostService {

    private final AuthUtiil authUtiil;
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
                .orElseThrow(NotFoundException::new);

        verificationAccount(post.getAccount());

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

    private void verificationAccount(Account account) {
        if(!(authUtiil.getAccount() == account)) {
            throw new ForbiddenException();
        }
    }

}
