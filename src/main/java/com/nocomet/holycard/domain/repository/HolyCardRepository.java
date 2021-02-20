package com.nocomet.holycard.domain.repository;

import com.nocomet.holycard.domain.entity.HolyCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolyCardRepository extends JpaRepository<HolyCard, Long> {
}
