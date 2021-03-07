package com.nocomet.holycard.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class ErrorResponseDto implements Serializable {
    private Integer code;
    private String error;
    private String description;
}
