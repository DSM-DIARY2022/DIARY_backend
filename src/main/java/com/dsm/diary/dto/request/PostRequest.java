package com.dsm.diary.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    @NotBlank
    @Size(max = 1)
    private Integer feeling;

    @NotBlank
    @Length(max = 100)
    private String title;

    @NotBlank
    @Length(max = 1000)
    private String content;

}
