package com.springboot.spring_task.exception_handing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedMediaTypeException extends RuntimeException{
    public UnsupportedMediaTypeException(String message) {
        super(message);
    }
}
