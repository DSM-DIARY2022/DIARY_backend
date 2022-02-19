package com.dsm.diary.dto.response;

import com.dsm.diary.entity.comments.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
public class PostResponse {

    @NotBlank
    private Long id;

    @NotBlank
    private Integer feeling;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private LocalDate createdDate;

    @NotBlank
    private LocalDate modifiedDate;

    private Comment comment;


}
