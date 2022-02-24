package com.dsm.diary.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409
public class AccountIdAlreadyExistsException extends RuntimeException{
}
