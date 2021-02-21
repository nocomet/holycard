package com.nocomet.holycard.domain.entity;

import com.nocomet.holycard.util.BooleanToYnConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@NoArgsConstructor
public class HolyCard extends VersionBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardSeq;

    private String imageName; // s3 bucket name

    private String content;

    private Long numberOfHeart = 0L;

    @Convert(converter = BooleanToYnConverter.class)
    private Boolean visible; // 이미지 노출 여부

    public HolyCard(String imageName, String content) {
        this.imageName = imageName;
        this.content = content;
        this.visible = false;
    }

    public void plusNumberOfHeart() {
        ++numberOfHeart;
    }

    public void minusNumberOfHeart() {
        --numberOfHeart;
    }
}
