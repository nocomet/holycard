package com.nocomet.holycard.domain.entity;

import com.nocomet.holycard.util.KoreanDateTime;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Setter
    @Column(nullable = true, name = "created_at")
    protected LocalDateTime createdAt;
    @Column(nullable = true, name = "updated_at")
    protected LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = KoreanDateTime.localNow();
        }
        this.updatedAt = KoreanDateTime.localNow();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = KoreanDateTime.localNow();
    }
}
