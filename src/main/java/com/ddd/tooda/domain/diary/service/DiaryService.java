package com.ddd.tooda.domain.diary.service;

import com.ddd.tooda.common.exception.BadRequestException;
import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.dto.DiaryDto.DiaryResponse;
import com.ddd.tooda.domain.diary.model.*;
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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MonthlyDiaryMetaRepository monthlyDiaryMetaRepository;
    private final DiaryQueryRepository diaryQueryRepository;

    @Transactional
    public DiaryResponse createDiary(Long userId, DiaryDto.CreateDiaryRequest req) {
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

        diary.setMonthlyDiaryMeta(monthlyDiaryMeta);
        monthlyDiaryMetaRepository.save(monthlyDiaryMeta);
        diaryRepository.save(diary);

        return DiaryResponse.from(diary);
    }

    @Transactional
    public void remove(Long userId, Long diaryId) {
        Diary diary = diaryQueryRepository.findByUserIdAndDiaryId(userId,diaryId);
        if (diary == null) {
            throw new BadRequestException();
        }
        diaryRepository.delete(diary);
        if(diary.getMonthlyDiaryMeta().getTotalCount() <= 1) {
            monthlyDiaryMetaRepository.delete(diary.getMonthlyDiaryMeta());
        } else {
            diary.getMonthlyDiaryMeta().decreaseTotalCount();
            monthlyDiaryMetaRepository.save(diary.getMonthlyDiaryMeta());
        }
    }

    @Transactional(readOnly = true)
    public List<DiaryResponse> findAll(Long userId, Long cursor, Integer limit) {
        List<Diary> diaries = diaryQueryRepository.findAllByNoOffset(userId, cursor, limit);
        return diaries.stream().map(DiaryResponse::from).collect(Collectors.toList());
    }

}
