package com.sreyas.skill.challenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

/**
 * This defines the structure for api response
 * Author : Sreyas V Pariyath
 * Date   : 08/12/20
 * Time   : 10:27 PM
 *
 * @param <T>
 */
@Builder
@Value
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    String error;
    String message;
    T data;
}
