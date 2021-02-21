package com.nocomet.holycard.controller;

import com.nocomet.holycard.controller.dto.ApiResult;
import com.nocomet.holycard.controller.dto.ResponseBaseDto;
import com.nocomet.holycard.domain.entity.HolyCard;
import com.nocomet.holycard.exception.ApiBaseException;
import com.nocomet.holycard.service.HolyCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/apis/v1/holy-card")
public class HolyCardController {

    private final HolyCardService holyCardService;

    @GetMapping("/{cardSeq}")
    public ResponseBaseDto<HolyCard> getHolyCard(@PathVariable Long cardSeq) throws ApiBaseException {
        HolyCard holyCard = holyCardService.find(cardSeq);
        return new ResponseBaseDto<>(ApiResult.OK, holyCard);
    }

    @PostMapping("/{cardSeq}/plus-heart")
    public ResponseBaseDto<HolyCard> plusHeart(@PathVariable Long cardSeq) throws ApiBaseException {
        HolyCard holyCard = holyCardService.plusHeart(cardSeq);
        return new ResponseBaseDto<>(ApiResult.OK, holyCard);
    }

    @PostMapping("/{cardSeq}/minus-heart")
    public ResponseBaseDto<HolyCard> minusHeart(@PathVariable Long cardSeq) throws ApiBaseException {
        HolyCard holyCard = holyCardService.minusHeart(cardSeq);
        return new ResponseBaseDto<>(ApiResult.OK, holyCard);
    }

    @PostMapping("/create")
        public ResponseBaseDto<HolyCard> createHolyCard(@RequestParam("filename") String filename,
                                                    @RequestParam("content") String content,
                                                    @RequestParam("imageFile") MultipartFile imageFile) {
        log.info("/create filename:{}", filename);
        HolyCard holyCard = holyCardService.saveCard(filename, content, imageFile);
        if (holyCard != null) {
            return new ResponseBaseDto<>(ApiResult.OK, holyCard);
        } else {
            return new ResponseBaseDto<>(ApiResult.FAIL, null);
        }
    }
}
