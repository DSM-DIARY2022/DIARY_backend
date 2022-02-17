package com.dsm.diary.entity.post;

import com.dsm.diary.entity.BaseTime;
import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.comments.Comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTime { // 게시글

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기분
    @Column(nullable = false, columnDefinition = "tinyint")
    private Integer feeling;

    // 제목
    @Column(nullable = false, length = 100)
    private String title;

    // 내용
    @Column(nullable = false, length = 1000)
    private String content;

    // 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private Comment comment;

    @Builder
    public Post(Integer feeling, String title, String content, Account account, Comment comment) {
        this.feeling = feeling;
        this.title = title;
        this.content = content;
        this.account = account;
        this.comment = comment;
    }

}
