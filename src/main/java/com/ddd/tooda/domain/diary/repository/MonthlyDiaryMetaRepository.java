package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.domain.diary.model.MonthlyDiaryMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyDiaryMetaRepository extends JpaRepository<MonthlyDiaryMeta, Long> {
}
