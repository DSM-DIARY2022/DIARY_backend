package com.dsm.diary.Exception;

import com.dsm.diary.Exception.Handler.DiaryException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends DiaryException {

    private static final int status = HttpStatus.UNAUTHORIZED.value();
    public UnauthorizedException(String message) {
        super(status, message);
    }


}
