package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.domain.diary.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<HashTag, Long> {
}
