package com.nocomet.holycard.controller;

import com.nocomet.holycard.controller.dto.ApiResult;
import com.nocomet.holycard.controller.dto.HolyCardDto;
import com.nocomet.holycard.controller.dto.ResponseBaseDto;
import com.nocomet.holycard.domain.entity.HolyCard;
import com.nocomet.holycard.exception.ApiBaseException;
import com.nocomet.holycard.service.HolyCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/apis/v1/holy-card")
public class HolyCardController {

    private final HolyCardService holyCardService;

    @GetMapping("/{cardSeq}")
    public ResponseBaseDto<HolyCardDto.Response> getHolyCard(@PathVariable Long cardSeq) throws ApiBaseException {
        HolyCard holyCard = holyCardService.find(cardSeq);
        String imageUrlBase = holyCardService.getImageUrlBase();

        return new ResponseBaseDto<>(ApiResult.OK,
            holyCardDtoResponse(holyCard, imageUrlBase)
        );
    }

    @PostMapping("/{cardSeq}/plus-heart")
    public ResponseBaseDto<HolyCardDto.Response> plusHeart(@PathVariable Long cardSeq) throws ApiBaseException {
        HolyCard holyCard = holyCardService.plusHeart(cardSeq);
        String imageUrlBase = holyCardService.getImageUrlBase();

        return new ResponseBaseDto<>(ApiResult.OK,
            holyCardDtoResponse(holyCard, imageUrlBase)
        );
    }

    @PostMapping("/{cardSeq}/minus-heart")
    public ResponseBaseDto<HolyCardDto.Response> minusHeart(@PathVariable Long cardSeq) throws ApiBaseException {
        HolyCard holyCard = holyCardService.minusHeart(cardSeq);
        String imageUrlBase = holyCardService.getImageUrlBase();

        return new ResponseBaseDto<>(ApiResult.OK,
            holyCardDtoResponse(holyCard, imageUrlBase)
        );
    }

    @PostMapping("/create")
    public ResponseBaseDto<HolyCardDto.Response> createHolyCard(@RequestParam("filename") String filename,
                                                                @RequestParam("content") String content,
                                                                @RequestParam("imageFile") MultipartFile imageFile) {
        log.info("/create filename:{}", filename);
        HolyCard holyCard = holyCardService.saveCard(filename, content, imageFile);
        if (holyCard != null) {
            String imageUrlBase = holyCardService.getImageUrlBase();
            return new ResponseBaseDto<>(ApiResult.OK,
                holyCardDtoResponse(holyCard, imageUrlBase)
            );
        } else {
            return new ResponseBaseDto<>(ApiResult.FAIL, null);
        }
    }

    @GetMapping(path = "/{cardSeq}/image.jpg", produces = "image/jpeg")
    public BufferedImage getHolyCardImage(@PathVariable Long cardSeq) throws ApiBaseException {
        HolyCard holyCard = holyCardService.find(cardSeq);
        String imageName = holyCard.getImageName();
        return holyCardService.getImage(imageName);
    }

    private HolyCardDto.Response holyCardDtoResponse(HolyCard holyCard, String imageUrlBase) {
        String imageUrl = imageUrlBase.replace("{cardSeq}", String.valueOf(holyCard.getCardSeq()));

        return HolyCardDto.Response.builder()
            .cardSeq(holyCard.getCardSeq())
            .imageUrl(imageUrl)
            .content(holyCard.getContent())
            .heart(holyCard.getNumberOfHeart())
            .build();
    }
}
