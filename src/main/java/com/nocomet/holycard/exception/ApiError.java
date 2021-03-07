package com.nocomet.holycard.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ApiError {

    UNKNOWN(0, "UNKNOWN", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(10001, "not found.", HttpStatus.NOT_FOUND)
    ;

    @Getter
    private Integer code;

    @Getter
    private String error;

    @Getter
    private HttpStatus httpStatus;

    ApiError(Integer code, String error, HttpStatus httpStatus) {
        this.code = code;
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
