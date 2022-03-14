package com.dsm.diary.Exception;

import com.dsm.diary.Exception.Handler.DiaryException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends DiaryException {

    private static final int status = HttpStatus.FORBIDDEN.value();
    public ForbiddenException(String message) {
        super(status, message);
    }

}
