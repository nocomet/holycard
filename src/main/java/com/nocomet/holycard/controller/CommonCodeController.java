package com.nocomet.holycard.controller;

import com.nocomet.holycard.controller.dto.ApiResult;
import com.nocomet.holycard.controller.dto.CommonCodeDto;
import com.nocomet.holycard.controller.dto.ResponseBaseDto;
import com.nocomet.holycard.domain.entity.CommonCode;
import com.nocomet.holycard.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/internal/v1/common-code")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;


    @GetMapping("/{key}")
    public ResponseBaseDto<CommonCode> get(@PathVariable String key) {
        CommonCode commonCode = commonCodeService.findNoCache(key);
        return new ResponseBaseDto<>(ApiResult.OK, commonCode);
    }

    @PutMapping("/{key}")
    public ResponseBaseDto<CommonCode> put(@PathVariable String key, @RequestBody CommonCodeDto.Request request) {
        CommonCode commonCode = commonCodeService.updateValue(key, request.getValue(), request.getDescription());
        return new ResponseBaseDto<>(ApiResult.OK, commonCode);
    }

    @GetMapping("/all")
    public ResponseBaseDto<List<CommonCode>> getAll() {
        List<CommonCode> list = commonCodeService.findAll();
        return new ResponseBaseDto<>(ApiResult.OK, list);
    }
}
