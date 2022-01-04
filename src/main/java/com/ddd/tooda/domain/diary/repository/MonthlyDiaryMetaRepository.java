package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.domain.diary.model.MonthlyDiaryMeta;
import com.ddd.tooda.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlyDiaryMetaRepository extends JpaRepository<MonthlyDiaryMeta, Long> {

    Optional<MonthlyDiaryMeta> findFirstByUserAndYearAndMonth(User user, int year, int month);
}
