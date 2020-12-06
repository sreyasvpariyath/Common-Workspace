package com.sreyas.skill.exception.handler;

import com.sreyas.skill.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

/**
 * Author : Sreyas V Pariyath
 * Date   : 03/12/20
 * Time   : 10:58 PM
 */
@ControllerAdvice
@Slf4j
public class DocumentControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity(buildApiResponse(ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        ConstraintViolationException exception = (ConstraintViolationException) ex;
        //array index..???
        String errorMessage = new ArrayList<>(exception.getConstraintViolations()).get(0).getMessage();
        return new ResponseEntity(buildApiResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }

    private ApiResponse buildApiResponse(String message) {
        return ApiResponse.builder()
                .error(message)
                .build();
    }
}
