package com.dsm.diary.Exception.Handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@Slf4j
@RestControllerAdvice
public class DiaryExceptionHandler {

    @ExceptionHandler(DiaryException.class)
    protected ResponseEntity<ErrorResponse> HandleDiaryException(final DiaryException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getStatus(), e.getMessage()), HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> HandleNullPointerException(final NullPointerException e) {
        return new ResponseEntity<>(new ErrorResponse(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ErrorResponse> HandleValidationException(final ValidationException e) {
        return new ResponseEntity<>(new ErrorResponse(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
