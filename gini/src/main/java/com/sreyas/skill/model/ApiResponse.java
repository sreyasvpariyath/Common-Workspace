package com.sreyas.skill.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Builder
@Value
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    String error;
    T data;
}
