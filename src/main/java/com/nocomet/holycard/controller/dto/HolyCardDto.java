package com.nocomet.holycard.controller.dto;

import lombok.*;

import java.io.Serializable;

public class HolyCardDto {

    @Builder
    @Getter
    @Setter
    public static class Response implements Serializable {
        private Long cardSeq;
        private String imageUrl;
        private String content;
        private Long heart;
    }
}
