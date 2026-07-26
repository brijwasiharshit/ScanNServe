package com.app.namasteqr.utils.api;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record StandardResponse<T> (
        T data,
        Boolean success,
        String message,
        Object errors,
        HttpStatus httpStatus){
}
