package com.example.diary_backend.Entity.Comments;

import com.example.diary_backend.Entity.Post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
