package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.common.NoOffsetPaginationDto;
import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.model.Diary;
import com.ddd.tooda.domain.diary.model.HashTag;
import com.ddd.tooda.domain.diary.model.MonthlyDiaryMeta;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.ddd.tooda.domain.diary.model.QDiary.diary;
import static com.ddd.tooda.domain.diary.model.QDiaryImage.diaryImage;
import static com.ddd.tooda.domain.diary.model.QDiaryLink.diaryLink;
import static com.ddd.tooda.domain.diary.model.QDiaryStock.diaryStock;
import static com.ddd.tooda.domain.diary.model.QHashTag.hashTag;
import static com.ddd.tooda.domain.diary.model.QMonthlyDiaryMeta.monthlyDiaryMeta;

@RequiredArgsConstructor
@Repository
public class DiaryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final JdbcTemplate jdbcTemplate;

    public Diary findByUserIdAndDiaryId(Long userId, Long diaryId) {
        return jpaQueryFactory.selectFrom(diary).where(
                diary.user.id.eq(userId),
                diary.id.eq(diaryId)
        ).join(diary.monthlyDiaryMeta, monthlyDiaryMeta).fetchJoin().fetchOne();
    }

    public List<Diary> findAll(Long userId, NoOffsetPaginationDto noOffsetPaginationDto) {
        return findAllDiaryByCondition(userId, noOffsetPaginationDto, null);
    }

    public List<Diary> findAllByDate(Long userId, DiaryDto.FindAllByDateRequest req) {
        BooleanExpression expression = diary.createdAt.between(req.getStartTime(), req.getEndTime());
        return findAllDiaryByCondition(userId, req.getNoOffsetPaginationDto(), expression);
    }

    public List<Diary> search(Long userId, String query, NoOffsetPaginationDto noOffsetPaginationDto) {
        BooleanExpression expression = diary.title.contains(query);
        return findAllDiaryByCondition(userId, noOffsetPaginationDto, expression);
    }

    private List<Diary> findAllDiaryByCondition(Long userId, NoOffsetPaginationDto noOffsetPaginationDto,
                                                BooleanExpression booleanExpression) {
        return jpaQueryFactory.selectFrom(diary).where(
                        diary.user.id.eq(userId),
                        ltCursorId(noOffsetPaginationDto.getCursor()),
                        booleanExpression
                )
                .leftJoin(diary.links, diaryLink).fetchJoin()
                .leftJoin(diary.stocks, diaryStock).fetchJoin()
                .leftJoin(diary.images, diaryImage).fetchJoin()
                .orderBy(diary.id.desc())
                .limit(noOffsetPaginationDto.getLimit())
                .fetch();
    }

    public List<MonthlyDiaryMeta> findMonthlyDiaryMetasByYear(Long userId, Integer year) {

        return null;
    }

    public List<HashTag> saveAllHashTag(List<String> tags) {
        String sql = "insert ignore into hash_tag (name) values (?)";
        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        String tag = tags.get(i);
                        ps.setString(1, tag);
                    }

                    @Override
                    public int getBatchSize() {
                        return tags.size();
                    }
                });

        return jpaQueryFactory.selectFrom(hashTag).where(
                hashTag.name.in(tags)
        ).fetch();
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        if (cursorId == null) {
            return null;
        }
        return diary.id.lt(cursorId);
    }


}
