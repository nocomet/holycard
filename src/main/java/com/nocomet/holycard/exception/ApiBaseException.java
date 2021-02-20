package com.nocomet.holycard.exception;

public class ApiBaseException extends Exception {

    private ApiError apiError;

    public ApiBaseException(ApiError apiError, String message) {
        super(message);
        this.apiError = apiError;
    }
}
