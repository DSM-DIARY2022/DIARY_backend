package com.dsm.diary.entity.post;

import com.dsm.diary.entity.BaseTime;
import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.comments.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends BaseTime { // 게시글

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    // 기분
    @Column(nullable = false, length = 1)
    private Integer feeling;

    // 제복
    @Column(nullable = false, length = 100)
    private String title;

    // 내용
    @Column(nullable = false, length = 1000)
    private String content;

    // 유저
    @ManyToOne
    @JoinColumn(name = "id")
    private Account account;

    // 댓글
    @OneToOne
    @JoinColumn(name = "comments_id")
    private Comment comment;

}
