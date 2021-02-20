package com.nocomet.holycard.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@MappedSuperclass
public abstract class VersionBaseEntity extends BaseEntity {
    @Version
    @Setter
    protected Long version;
}
