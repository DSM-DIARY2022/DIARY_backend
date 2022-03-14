package com.dsm.diary.Exception;

import com.dsm.diary.Exception.Handler.DiaryException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends DiaryException {

    private static final int status = HttpStatus.BAD_REQUEST.value();
    public BadRequestException(String message) {
        super(status, message);
    }

}
