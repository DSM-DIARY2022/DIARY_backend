package com.dsm.diary.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequest {
    @NotBlank
    @Size(min = 1, max = 300, message = "댓글은 1~300글자 이내로 입력해주세요.")
    private String comment;
}
