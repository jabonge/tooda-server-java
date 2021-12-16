package com.ddd.tooda.domain.diary.service;

import com.ddd.tooda.common.exception.BadRequestException;
import com.ddd.tooda.domain.diary.model.Diary;
import com.ddd.tooda.domain.diary.model.MonthlyDiaryMeta;
import com.ddd.tooda.domain.diary.repository.DiaryQueryRepository;
import com.ddd.tooda.domain.diary.repository.DiaryRepository;
import com.ddd.tooda.domain.diary.repository.MonthlyDiaryMetaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;


@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    private final Long userId = 1L;
    private final Long diaryId = 1L;
    private final LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    @InjectMocks
    private DiaryService diaryService;
    @Mock
    private MonthlyDiaryMetaRepository monthlyDiaryMetaRepository;
    @Mock
    private DiaryRepository diaryRepository;
    @Mock
    private DiaryQueryRepository diaryQueryRepository;

    @Test
    @Disabled
        //어떤식으로 테스트를 해야 될까? 정답을 알려줘
    void createDiary() {

    }

    @Test
    @DisplayName("올바르지 않은 파라미터")
    void remove_test1() {

        Mockito.doReturn(null).when(diaryQueryRepository).findByUserIdAndDiaryId(userId, diaryId);
        Assertions.assertThrows(BadRequestException.class, () -> diaryService.remove(userId, diaryId));
    }

    @Test
    @DisplayName("if totalCount <= 1")
    void remove_test2() {

        Diary diary = Diary.builder().title("타이틀").content("콘텐츠").userId(userId).build();

        MonthlyDiaryMeta monthlyDiaryMeta =
                MonthlyDiaryMeta.builder().userId(userId).year(now.getYear()).month(now.getMonthValue()).build();
        diary.setMonthlyDiaryMeta(monthlyDiaryMeta);

        Mockito.doReturn(diary).when(diaryQueryRepository).findByUserIdAndDiaryId(userId, diaryId);
        diaryService.remove(userId, diaryId);

        Mockito.verify(monthlyDiaryMetaRepository, Mockito.times(1)).delete(monthlyDiaryMeta);

    }

    @Test
    @DisplayName("if totalCount > 1")
    void remove_test3() {
        Diary diary = Diary.builder().title("타이틀").content("콘텐츠").userId(userId).build();
        MonthlyDiaryMeta monthlyDiaryMeta =
                MonthlyDiaryMeta.builder().userId(userId).year(now.getYear()).month(now.getMonthValue()).build();
        monthlyDiaryMeta.increaseTotalCount();
        diary.setMonthlyDiaryMeta(monthlyDiaryMeta);

        Mockito.doReturn(diary).when(diaryQueryRepository).findByUserIdAndDiaryId(userId, diaryId);
        diaryService.remove(userId, diaryId);

        Mockito.verify(monthlyDiaryMetaRepository, Mockito.times(1)).save(diary.getMonthlyDiaryMeta());
    }
}