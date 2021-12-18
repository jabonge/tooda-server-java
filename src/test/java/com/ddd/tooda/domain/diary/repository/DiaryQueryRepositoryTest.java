package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.common.NoOffsetPaginationDto;
import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.model.Diary;
import com.ddd.tooda.domain.diary.model.HashTag;
import com.ddd.tooda.domain.diary.model.type.Sticker;
import com.ddd.tooda.domain.diary.service.DiaryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class DiaryQueryRepositoryTest {

    @Autowired
    private DiaryQueryRepository diaryQueryRepository;
    @Autowired
    private DiaryService diaryService;


    @BeforeAll
    void setUp() {
        for (int i = 0; i < 10; i++) {
            DiaryDto.CreateDiaryRequest req = new DiaryDto.CreateDiaryRequest("타이틀", "콘텐츠", Sticker.OMG,
                    new ArrayList<>(), Arrays.asList("https" +
                    "://tooda-test.s3.ap-northeast-2.amazonaws.com/1622123707242.jpeg"), new ArrayList<>());
            diaryService.createDiary(1L, req);
        }
    }

    @Test
    void findByUserIdAndDiaryId() {
        Diary diary = diaryQueryRepository.findByUserIdAndDiaryId(1L, 1L);
        Assertions.assertEquals(diary.getTitle(), "타이틀");
        Assertions.assertEquals(diary.getMonthlyDiaryMeta().getTotalCount(), 10);
    }

    @Test
    void findAll() {
        List<Diary> diaries = diaryQueryRepository.findAll(1L, new NoOffsetPaginationDto(6L, 20));
        Assertions.assertEquals(diaries.size(), 5);

        Diary diary = diaries.get(0);
        Assertions.assertEquals(diary.getImages().size(), 1);
        Assertions.assertEquals(diary.getId(), 5L);
    }

    @Test
    void findAllByDate() {
        DiaryDto.FindAllByDateRequest req = new DiaryDto.FindAllByDateRequest(2021, 12,
                new NoOffsetPaginationDto(null, 20));

        List<Diary> diaries = diaryQueryRepository.findAllByDate(1L, req);
        Assertions.assertEquals(diaries.size(), 10);

        Diary diary = diaries.get(0);
        Assertions.assertEquals(diary.getImages().size(), 1);
        Assertions.assertEquals(diary.getId(), 10L);
    }

    @Test
    void search() {
        List<Diary> success = diaryQueryRepository.search(1L, "이틀", new NoOffsetPaginationDto(null,20));
        Assertions.assertEquals(success.size(), 10);

        List<Diary> failure = diaryQueryRepository.search(1L, "바보", new NoOffsetPaginationDto(null,20));
        Assertions.assertEquals(failure.isEmpty(), true);
    }

    @Test
    void saveAllHashTag() {
        List<HashTag> hashTags = diaryQueryRepository.saveAllHashTag(Arrays.asList("바보","멍청이"));
        Assertions.assertEquals(hashTags.size(), 2);
        Assertions.assertNotNull(hashTags.get(0).getId());
    }

    @Test
    void findMonthlyDiaryMetasByYear() {
        diaryQueryRepository.findMonthlyDiaryMetasByYear(1L,2021);
    }
}