package com.nocomet.holycard.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@NoArgsConstructor
public class HolyCard extends VersionBaseEntity {

    @Id
    @GeneratedValue
    private Long cardSeq;

    private String cardImageUrl;

    private String content;

    private Long numberOfHeart = 0L;

    public HolyCard(String cardImageUrl, String content) {
        this.cardImageUrl = cardImageUrl;
        this.content = content;
    }

    public void plusNumberOfHeart() {
        ++numberOfHeart;
    }

    public void minusNumberOfHeart() {
        --numberOfHeart;
    }
}
