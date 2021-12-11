package com.ddd.tooda.domain.diary.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DiaryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
