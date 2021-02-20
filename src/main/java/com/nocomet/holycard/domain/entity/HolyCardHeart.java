package com.nocomet.holycard.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class HolyCardHeart extends BaseEntity {
    @Id
    private Long heartSeq;
    private Long userSeq;
    private Long cardSeq;
}
