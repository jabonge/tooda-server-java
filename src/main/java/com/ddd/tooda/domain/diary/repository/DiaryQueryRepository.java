package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.domain.diary.model.Diary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.ddd.tooda.domain.diary.model.QDiary.diary;
import static com.ddd.tooda.domain.diary.model.QDiaryImage.diaryImage;
import static com.ddd.tooda.domain.diary.model.QDiaryLink.diaryLink;
import static com.ddd.tooda.domain.diary.model.QDiaryStock.diaryStock;
import static com.ddd.tooda.domain.diary.model.QMonthlyDiaryMeta.monthlyDiaryMeta;

@RequiredArgsConstructor
@Repository
public class DiaryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Diary findByUserIdAndDiaryId(Long userId, Long diaryId) {
        return jpaQueryFactory.selectFrom(diary).where(
                diary.user.id.eq(userId),
                diary.id.eq(diaryId)
        ).join(diary.monthlyDiaryMeta,monthlyDiaryMeta).fetchJoin().fetchOne();
    }

    public List<Diary> findAllByNoOffset(Long userId, Long cursor, Integer limit) {
        return jpaQueryFactory.selectFrom(diary).where(
                diary.user.id.eq(userId),
                ltDiaryId(cursor)
        ).leftJoin(diary.links, diaryLink).fetchJoin()
                .leftJoin(diary.stocks,diaryStock).fetchJoin()
                .leftJoin(diary.images,diaryImage).fetchJoin()
                .orderBy(diary.id.desc())
                .limit(limit)
                .fetch();
    }

    private BooleanExpression ltDiaryId(Long diaryId) {
        if(diaryId == null) {
            return null;
        }
        return diary.id.lt(diaryId);
    }

}
