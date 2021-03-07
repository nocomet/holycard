package com.nocomet.holycard.exception;

import lombok.Getter;

public class ApiBaseException extends Exception {

    @Getter
    private ApiError apiError;

    public ApiBaseException(ApiError apiError, String message) {
        super(message);
        this.apiError = apiError;
    }
}
