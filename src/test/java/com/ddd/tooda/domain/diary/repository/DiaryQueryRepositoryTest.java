package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.model.Diary;
import com.ddd.tooda.domain.diary.model.type.Sticker;
import com.ddd.tooda.domain.diary.service.DiaryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DiaryQueryRepositoryTest {

    @Autowired
    private DiaryQueryRepository diaryQueryRepository;
    @Autowired
    private DiaryService diaryService;


    @BeforeAll
    void setUp() {
        for (int i = 0; i< 10; i++) {
            DiaryDto.CreateDiaryRequest req = new DiaryDto.CreateDiaryRequest("타이틀", "콘텐츠", Sticker.OMG,
                    new ArrayList<>(), Arrays.asList("https" +
                    "://tooda-test.s3.ap-northeast-2.amazonaws.com/1622123707242.jpeg"), new ArrayList<>());
            diaryService.createDiary(1L,req);
        }
    }

    @Test
    void findByUserIdAndDiaryId() {
        Diary diary = diaryQueryRepository.findByUserIdAndDiaryId(1L, 1L);
        Assertions.assertEquals(diary.getTitle(), "타이틀");
        Assertions.assertEquals(diary.getMonthlyDiaryMeta().getTotalCount(), 10);
    }

    @Test
    void findAllByNoOffset() {
        List<Diary> diaries = diaryQueryRepository.findAllByNoOffset(1L, 6L, 20);
        Assertions.assertEquals(diaries.size(), 5);

        Diary diary = diaries.get(0);
        Assertions.assertEquals(diary.getImages().size(), 1);
        Assertions.assertEquals(diary.getId(), 5L);
    }
}