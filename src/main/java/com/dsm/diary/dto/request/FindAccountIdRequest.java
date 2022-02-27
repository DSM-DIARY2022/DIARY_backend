package com.dsm.diary.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindAccountIdRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "email 형식으로 작성해주세요.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(min = 1, max = 10, message = "닉네임은 1~10글자 이내로 입력해주세요")
    private String name;
}
