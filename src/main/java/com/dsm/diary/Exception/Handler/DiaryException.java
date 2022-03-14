package com.dsm.diary.Exception.Handler;

import lombok.Getter;

@Getter
public class DiaryException extends RuntimeException {

    private final int status;
    private final String message;

    public DiaryException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

}
