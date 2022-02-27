package com.dsm.diary.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindAccountIdRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "email 형식으로 작성해주세요.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String name;
}
