package com.dsm.diary.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "email 형식으로 작성해주세요.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp="[a-zA-Z1-9]{5,10}$", message = "아이디는 영문자와 숫자를 모두 포함해서 5~10자리 이내로 입력해주세요.")
    private String accountId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}$",
            message = "비밀번호는 영문자와 숫자, 특수문자를 모두 포함해서 6~18자리 이내로 입력해주세요.")
    private String password;

    public void encodePassword(String password){
        this.password = password;
    }

    /*
        - 이메일
        - 아이디(5~10, 영문자와 숫자 모두 포함)
        - 비밀번호(6~12 영문자, 숫자, 특수기호 모두 포함)
        - 닉네임
    */

}
