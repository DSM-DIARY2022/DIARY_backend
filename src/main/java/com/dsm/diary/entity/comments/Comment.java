package com.dsm.diary.entity.comments;

import com.dsm.diary.entity.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment { // 댓글

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 내용
    @Column(nullable = false, length = 500)
    private String content;

    // 댓글
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(String content, Post post) {
        this.content = content;
        this.post = post;
    }

    public Comment updateComment(String content){
        this.content = content;
        return this;
    }
}
