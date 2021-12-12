package com.ddd.tooda.domain.diary.service;

import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.model.*;
import com.ddd.tooda.domain.diary.repository.DiaryQueryRepository;
import com.ddd.tooda.domain.diary.repository.DiaryRepository;
import com.ddd.tooda.domain.diary.repository.HashtagRepository;
import com.ddd.tooda.domain.diary.repository.MonthlyDiaryMetaRepository;
import com.ddd.tooda.util.HashtagUtil;
import com.ddd.tooda.util.OpenGraphUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MonthlyDiaryMetaRepository monthlyDiaryMetaRepository;
    private final HashtagRepository hashtagRepository;
    private final DiaryQueryRepository diaryQueryRepository;

    @Transactional
    public DiaryDto.DiaryResponse createDiary(Long userId, DiaryDto.CreateDiaryRequest req) {
        List<HashTag> hashtags = HashtagUtil.findHashtags(req.getContent()).stream().map(v -> new HashTag(null, v)).collect(Collectors.toList());
        List<DiaryStock> stocks = req.getStocks().stream().map(v -> v.toEntity()).collect(Collectors.toList());
        List<DiaryImage> images = req.getImages().stream().map(v -> new DiaryImage(null, v, null)).collect(Collectors.toList());
        List<DiaryLink> links = req.getLinks().stream().map(v -> OpenGraphUtil.getOgInfo(v)).filter(Objects::nonNull).map(o -> o.toEntity()).collect(Collectors.toList());

        if (!hashtags.isEmpty()) {
            hashtags = hashtagRepository.saveAll(hashtags);
        }

        Diary diary = Diary.builder().title(req.getTitle())
                .content(req.getContent())
                .sticker(req.getSticker())
                .images(images)
                .hashtags(hashtags)
                .stocks(stocks)
                .userId(userId)
                .links(links).build();

        diary = diaryRepository.save(diary);

        return DiaryDto.DiaryResponse.from(diary);

    }

}
