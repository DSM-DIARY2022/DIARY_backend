package com.dsm.diary.entity.comments;

import com.dsm.diary.entity.post.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comments { // 댓글

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private Long id;

    // 내용
    @Column(nullable = false, length = 500)
    private String content;

    // 게시글
    @OneToOne(mappedBy = "comments")
    private Post post;


}
