package com.ddd.tooda.domain.diary.service;

import com.ddd.tooda.common.exception.BadRequestException;
import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.model.*;
import com.ddd.tooda.domain.diary.repository.DiaryQueryRepository;
import com.ddd.tooda.domain.diary.repository.DiaryRepository;
import com.ddd.tooda.domain.diary.repository.MonthlyDiaryMetaRepository;
import com.ddd.tooda.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;



@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MonthlyDiaryMetaRepository monthlyDiaryMetaRepository;
    private final DiaryQueryRepository diaryQueryRepository;

    @Transactional
    public DiaryDto.DiaryResponse createDiary(Long userId, DiaryDto.CreateDiaryRequest req) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        MonthlyDiaryMeta monthlyDiaryMeta =
                monthlyDiaryMetaRepository.findFirstByUserAndYearAndMonth(new User(userId), now.getYear(),
                        now.getMonthValue()).orElse(null);

        if (monthlyDiaryMeta == null) {
            monthlyDiaryMeta =
                    MonthlyDiaryMeta.builder()
                            .year(now.getYear())
                            .month(now.getMonthValue())
                            .userId(userId)
                            .build();
        } else {
            monthlyDiaryMeta.increaseTotalCount();
        }

        Diary diary = req.toEntity(userId);
        monthlyDiaryMeta.addDiary(diary);
        monthlyDiaryMetaRepository.save(monthlyDiaryMeta);

        return DiaryDto.DiaryResponse.from(diary);
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

}
