package com.nocomet.holycard.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseBaseDto<R> {
    private ApiResult result;
    private R body;
}
