package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.domain.diary.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Long, Diary> {
}
