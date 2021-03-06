package com.nocomet.holycard.controller.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public class HolyCardDto {

    @Builder
    @Getter
    @Setter
    public static class ListResponse implements Serializable {
        private Page<Response> page;
    }

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
