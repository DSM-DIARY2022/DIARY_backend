package com.dsm.diary.Exception;

import com.dsm.diary.Exception.Handler.DiaryException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends DiaryException {

    private static final int status = HttpStatus.NOT_FOUND.value();
    public NotFoundException(String message) {
        super(status, message);
    }

}
