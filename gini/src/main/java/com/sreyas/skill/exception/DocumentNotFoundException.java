package com.sreyas.skill.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.sreyas.skill.constants.Constants.DOCUMENT_NOT_FOUND;

/**
 * Author : Sreyas V Pariyath
 * Date   : 03/12/20
 * Time   : 10:54 PM
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason=DOCUMENT_NOT_FOUND)
public class DocumentNotFoundException extends RuntimeException {

}
