package com.nocomet.holycard.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class CommonCode extends VersionBaseEntity {

    @Id
    private String commonKey;
    private String value;
    private String description;

    public CommonCode(String commonKey, String value, String description) {
        this.commonKey = commonKey;
        this.value = value;
        this.description = description;
    }
}
