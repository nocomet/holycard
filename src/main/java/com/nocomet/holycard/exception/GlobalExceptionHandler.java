package com.nocomet.holycard.exception;

import com.nocomet.holycard.controller.dto.ApiResult;
import com.nocomet.holycard.controller.dto.ErrorResponseDto;
import com.nocomet.holycard.controller.dto.ResponseBaseDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

import static com.nocomet.holycard.exception.ApiError.UNKNOWN;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiBaseException.class)
    public ResponseEntity<ResponseBaseDto<ErrorResponseDto>> handleApiBaseException(ApiBaseException apiBaseException) {
        ApiError apiError = Optional.ofNullable(apiBaseException.getApiError()).orElse(UNKNOWN);
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
            .code(apiError.getCode())
            .error(apiError.getError())
            .description(apiBaseException.getMessage())
            .build();

        ResponseBaseDto<ErrorResponseDto> responseBaseDto = new ResponseBaseDto<>(ApiResult.FAIL, errorResponseDto);
        return ResponseEntity.status(apiError.getHttpStatus()).body(responseBaseDto);
    }
}
