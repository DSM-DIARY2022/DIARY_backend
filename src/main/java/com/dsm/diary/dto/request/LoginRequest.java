package com.dsm.diary.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String accountId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    /*
    - 아이디
    - 비밀번호
    */

}
