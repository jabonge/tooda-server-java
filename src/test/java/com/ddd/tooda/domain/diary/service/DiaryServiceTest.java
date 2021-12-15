package com.ddd.tooda.domain.diary.service;

import com.ddd.tooda.domain.diary.repository.DiaryQueryRepository;
import com.ddd.tooda.domain.diary.repository.DiaryRepository;
import com.ddd.tooda.domain.diary.repository.MonthlyDiaryMetaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    @InjectMocks
    private DiaryService diaryService;

    @Mock
    private MonthlyDiaryMetaRepository monthlyDiaryMetaRepository;
    @Mock
    private DiaryRepository diaryRepository;
    @Mock
    private DiaryQueryRepository diaryQueryRepository;

    @Test
    void createDiary() {
    }

    @Test
    void remove() {
    }
}