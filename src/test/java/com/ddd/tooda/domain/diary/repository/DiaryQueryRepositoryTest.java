package com.ddd.tooda.domain.diary.repository;

import com.ddd.tooda.common.NoOffsetPaginationDto;
import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.dto.MonthlyDiaryMetaDto.MonthlyDiaryMetaResponse;
import com.ddd.tooda.domain.diary.model.Diary;
import com.ddd.tooda.domain.diary.model.HashTag;
import com.ddd.tooda.domain.diary.model.type.Sticker;
import com.ddd.tooda.domain.diary.service.DiaryService;
import com.ddd.tooda.domain.user.model.User;
import com.ddd.tooda.domain.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class DiaryQueryRepositoryTest {

    @Autowired
    private DiaryQueryRepository diaryQueryRepository;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private MonthlyDiaryMetaRepository monthlyDiaryMetaRepository;
    @Autowired
    private DiaryService diaryService;
    @Autowired
    private UserService userService;

    private User user;
    private Long lastDiaryId;
    private LocalDate localDate;

    @BeforeAll
    void setUp() {
        String deviceId = "ABCD_EFGWER_GWEERU_ABCD_EFGWER_GWEER";
        user = userService.signUp(deviceId);
        localDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        for (int i = 0; i < 10; i++) {
            DiaryDto.CreateDiaryRequest req = new DiaryDto.CreateDiaryRequest("타이틀", "콘텐츠", Sticker.OMG,
                    new ArrayList<>(), Arrays.asList("https" +
                    "://tooda-test.s3.ap-northeast-2.amazonaws.com/1622123707242.jpeg"), new ArrayList<>());
            lastDiaryId = diaryService.createDiary(user.getId(), req).getId();
        }
    }

    @AfterAll
    void clean() {
        diaryRepository.deleteAll();
        monthlyDiaryMetaRepository.deleteAll();
    }

    @Test
    void findByUserIdAndDiaryId() {
        Diary diary = diaryQueryRepository.findByUserIdAndDiaryId(user.getId(), lastDiaryId);
        assertEquals(diary.getTitle(), "타이틀");
        assertEquals(diary.getMonthlyDiaryMeta().getTotalCount(), 10);
    }

    @Test
    void findAll() {
        List<Diary> diaries = diaryQueryRepository.findAll(user.getId(), new NoOffsetPaginationDto(lastDiaryId-4L, 20));
        assertEquals(diaries.size(), 5);

        Diary diary = diaries.get(0);
        assertEquals(diary.getImages().size(), 1);
        assertEquals(diary.getId(), lastDiaryId-5L);
    }

    @Test
    void findAllByDate() {
        DiaryDto.FindAllByDateRequest req = new DiaryDto.FindAllByDateRequest(localDate.getYear(), localDate.getMonthValue());

        List<Diary> diaries = diaryQueryRepository.findAllByDate(user.getId(), req, new NoOffsetPaginationDto(null, 20));
        assertEquals(diaries.size(), 10);

        Diary diary = diaries.get(0);
        assertEquals(diary.getImages().size(), 1);
        assertEquals(diary.getId(), lastDiaryId);
    }

    @Test
    void search() {
        List<Diary> success = diaryQueryRepository.search(user.getId(), "타이", new NoOffsetPaginationDto(null, 20));
        assertEquals(success.size(), 10);

        List<Diary> failure = diaryQueryRepository.search(user.getId(), "콘텐츠", new NoOffsetPaginationDto(null, 20));
        assertTrue(failure.isEmpty());
    }

    @Test
    void saveAllHashTag() {
        List<HashTag> hashTags = diaryQueryRepository.saveAllHashTag(Arrays.asList("전기차","금융"));
        assertEquals(hashTags.size(), 2);
        assertNotNull(hashTags.get(0).getId());
    }

    @Test
    void findMonthlyDiaryMetasByYear() {
        List<MonthlyDiaryMetaResponse> responses = diaryQueryRepository.findMonthlyDiaryMetasByYear(user.getId(), localDate.getYear());
        assertEquals(responses.get(0).getStickers().size(), 3);
        assertEquals(responses.get(0).getStickers().get(0), Sticker.OMG.getName());
    }
}