package com.dsm.diary.dto.response;

import javax.validation.constraints.NotBlank;

public class PostIdResponse {

    @NotBlank
    private Long id;

    public PostIdResponse(Long id) {
        this.id = id;
    }

}
