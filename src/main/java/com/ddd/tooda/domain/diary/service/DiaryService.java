package com.ddd.tooda.domain.diary.service;

import com.ddd.tooda.common.NoOffsetPaginationDto;
import com.ddd.tooda.common.exception.BadRequestException;
import com.ddd.tooda.domain.diary.dto.MonthlyDiaryMetaDto.MonthlyDiaryMetaResponse;
import com.ddd.tooda.domain.diary.model.Diary;
import com.ddd.tooda.domain.diary.model.HashTag;
import com.ddd.tooda.domain.diary.model.MonthlyDiaryMeta;
import com.ddd.tooda.domain.diary.repository.DiaryQueryRepository;
import com.ddd.tooda.domain.diary.repository.DiaryRepository;
import com.ddd.tooda.domain.diary.repository.MonthlyDiaryMetaRepository;
import com.ddd.tooda.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.ddd.tooda.domain.diary.dto.DiaryDto.*;


@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MonthlyDiaryMetaRepository monthlyDiaryMetaRepository;
    private final DiaryQueryRepository diaryQueryRepository;

    @Transactional
    public DiaryDetailResponse createDiary(Long userId, CreateDiaryRequest req) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        MonthlyDiaryMeta monthlyDiaryMeta =
                monthlyDiaryMetaRepository.findFirstByUserAndYearAndMonth(new User(userId), now.getYear(),
                        now.getMonthValue()).orElse(MonthlyDiaryMeta.builder()
                        .year(now.getYear())
                        .month(now.getMonthValue())
                        .userId(userId)
                        .build());

        monthlyDiaryMeta.increaseTotalCount();

        Diary diary = req.toEntity(userId);
        List<String> extractTags = req.getHashTags();
        if(extractTags != null) {
            List<HashTag> tags = diaryQueryRepository.saveAllHashTag(req.getHashTags());
            diary.setHashTags(tags);
        }

        monthlyDiaryMeta.addDiary(diary);

        monthlyDiaryMetaRepository.save(monthlyDiaryMeta);
        diaryRepository.save(diary);

        return DiaryDetailResponse.from(diary);
    }

    @Transactional
    public DiaryDetailResponse updateDiary(Long userId, Long diaryId, CreateDiaryRequest req) {

        Diary diary = diaryQueryRepository.findOne(userId,diaryId);
        if (diary == null) {
            throw new BadRequestException();
        }
        diary.merge(req.toEntity(userId));

        List<String> extractTags = req.getHashTags();
        if(extractTags != null) {
            List<HashTag> tags = diaryQueryRepository.saveAllHashTag(req.getHashTags());
            diary.setHashTags(tags);
        }

        diary.getMonthlyDiaryMeta().setUpdatedAtToNow();
        monthlyDiaryMetaRepository.save(diary.getMonthlyDiaryMeta());
        diaryRepository.save(diary);
        return DiaryDetailResponse.from(diary);
    }

    @Transactional
    public void remove(Long userId, Long diaryId) {
        Diary diary = diaryQueryRepository.findByUserIdAndDiaryId(userId, diaryId);
        if (diary == null) {
            throw new BadRequestException();
        }
        diaryRepository.delete(diary);
        if (diary.getMonthlyDiaryMeta().getTotalCount() <= 1) {
            monthlyDiaryMetaRepository.delete(diary.getMonthlyDiaryMeta());
        } else {
            diary.getMonthlyDiaryMeta().decreaseTotalCount();
            monthlyDiaryMetaRepository.save(diary.getMonthlyDiaryMeta());
        }
    }

    @Transactional(readOnly = true)
    public DiaryDetailResponse findOne(Long userId, Long diaryId) {
        Diary diary = diaryQueryRepository.findOne(userId,diaryId);
        return DiaryDetailResponse.from(diary);
    }

    @Transactional(readOnly = true)
    public NoOffsetDiaryResponses findAll(Long userId, NoOffsetPaginationDto noOffsetPaginationDto) {
        List<Diary> diaries = diaryQueryRepository.findAll(userId, noOffsetPaginationDto);
        return NoOffsetDiaryResponses.of(diaries, noOffsetPaginationDto.getLimit());
    }

    @Transactional(readOnly = true)
    public NoOffsetDiaryResponses findAllByDate(Long userId, FindAllByDateRequest req, NoOffsetPaginationDto noOffsetPaginationDto) {
        List<Diary> diaries = diaryQueryRepository.findAllByDate(userId, req, noOffsetPaginationDto);
        return NoOffsetDiaryResponses.of(diaries, noOffsetPaginationDto.getLimit());
    }

    @Transactional(readOnly = true)
    public NoOffsetDiaryResponses search(Long userId, String query, NoOffsetPaginationDto noOffsetPaginationDto) {
        List<Diary> diaries = diaryQueryRepository.search(userId, query, noOffsetPaginationDto);
        return NoOffsetDiaryResponses.of(diaries, noOffsetPaginationDto.getLimit());
    }

    @Transactional(readOnly = true)
    public List<MonthlyDiaryMetaResponse> findMonthlyDiaryMetasByYear(Long userId, int year) {
        return diaryQueryRepository.findMonthlyDiaryMetasByYear(userId, year);
    }

}
