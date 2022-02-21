package com.dsm.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
public class PostViweResponse {

    @NotBlank
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private LocalDate createdDate;

    @NotBlank
    private LocalDate modifiedDate;

}
