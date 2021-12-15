package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.domain.diary.model.Diary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.ddd.tooda.domain.diary.model.QDiary.diary;
import static com.ddd.tooda.domain.diary.model.QMonthlyDiaryMeta.monthlyDiaryMeta;

@RequiredArgsConstructor
@Repository
public class DiaryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Diary findByUserIdAndDiaryId(Long userId, Long diaryId) {
        return jpaQueryFactory.selectFrom(diary).where(
                diary.user.id.eq(userId),
                diary.id.eq(diaryId)
        ).join(monthlyDiaryMeta).on(diary.monthlyDiaryMeta.id.eq(monthlyDiaryMeta.id)).fetchOne();
    }

}
