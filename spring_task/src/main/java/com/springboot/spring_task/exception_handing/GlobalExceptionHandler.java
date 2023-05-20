package com.springboot.spring_task.exception_handing;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("message", errors);
        return errorResponse;
    }
    @ExceptionHandler(MethodNotAllowedException.class)
    public ErrorResponse handleException(MethodNotAllowedException e){
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleException(NotFoundException e){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleException(BadRequestException e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ErrorResponse handleException(UnsupportedMediaTypeException e){
        return new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), e.getMessage());
    }
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Throwable e){
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
