package com.springboot.spring_task.exception_handing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends RuntimeException{
    public MethodNotAllowedException(String message) {
        super(message);
    }
}
