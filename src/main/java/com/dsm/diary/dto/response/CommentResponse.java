package com.dsm.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;

    @NotBlank
    @Size(min = 1, max = 500, message = "댓글은 1~500글자 이내로 입력해주세요.")
    private String content;
}
