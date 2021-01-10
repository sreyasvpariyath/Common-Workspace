package com.sreyas.skill.challenge.exception.handler;

import lombok.extern.slf4j.Slf4j;
import com.sreyas.skill.challenge.exception.AdminServiceException;
import com.sreyas.skill.challenge.exception.DocumentNotFoundException;
import com.sreyas.skill.challenge.exception.FileUploadException;
import com.sreyas.skill.challenge.model.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static com.sreyas.skill.challenge.constants.Constants.*;

/**
 * Author : Sreyas V Pariyath
 * Date   : 03/12/20
 * Time   : 10:58 PM
 */
@ControllerAdvice
@Slf4j
public class DocumentControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DocumentNotFoundException.class)
    public final ResponseEntity<Object> handleDocumentNotFoundException(DocumentNotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity(buildApiResponse(DOCUMENT_NOT_FOUND), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(FileUploadException.class)
    public final ResponseEntity<Object> handleFileUploadException(FileUploadException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity(buildApiResponse(FILE_UPLOAD_FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AdminServiceException.class)
    public final ResponseEntity<Object> handleFileUploadException(AdminServiceException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity(buildApiResponse(ADMIN_SERVICE_EXCEPTION), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity(buildApiResponse(String.join(", ", validationList)), HttpStatus.BAD_REQUEST);
    }

    private ApiResponse buildApiResponse(String message) {
        return ApiResponse.builder()
                .error(message)
                .build();
    }
}
