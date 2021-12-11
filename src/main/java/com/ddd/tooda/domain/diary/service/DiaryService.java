package com.ddd.tooda.domain.diary.service;

import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.repository.DiaryQueryRepository;
import com.ddd.tooda.domain.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryQueryRepository diaryQueryRepository;

//    public DiaryDto.DiaryResponse createDiary(Long userId, DiaryDto.CreateDiaryRequest req) {
//
//    }

}
