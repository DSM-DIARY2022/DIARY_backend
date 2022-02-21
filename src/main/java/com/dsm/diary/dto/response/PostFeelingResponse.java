package com.dsm.diary.dto.response;

import javax.validation.constraints.NotBlank;

public class PostFeelingResponse {

    @NotBlank
    private Integer feeling;

    public PostFeelingResponse(Integer feeling) {
        this.feeling = feeling;
    }

}
