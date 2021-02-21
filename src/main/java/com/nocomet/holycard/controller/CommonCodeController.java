package com.nocomet.holycard.controller;

import com.nocomet.holycard.controller.dto.ApiResult;
import com.nocomet.holycard.controller.dto.ResponseBaseDto;
import com.nocomet.holycard.domain.entity.CommonCode;
import com.nocomet.holycard.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/internal/v1/common-code")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;


    @GetMapping("/{key}")
    public ResponseBaseDto<CommonCode> get(@PathVariable String key) {
        CommonCode commonCode = commonCodeService.find(key);
        return new ResponseBaseDto<>(ApiResult.OK, commonCode);
    }

    @GetMapping("/all")
    public ResponseBaseDto<List<CommonCode>> getAll() {
        List<CommonCode> list = commonCodeService.findAll();
        return new ResponseBaseDto<>(ApiResult.OK, list);
    }
}
