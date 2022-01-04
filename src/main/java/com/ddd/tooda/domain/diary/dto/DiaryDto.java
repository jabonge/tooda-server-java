package com.ddd.tooda.domain.diary.dto;

import com.ddd.tooda.common.NoOffsetPaginationDto;
import com.ddd.tooda.domain.diary.model.*;
import com.ddd.tooda.domain.diary.model.type.ChangeType;
import com.ddd.tooda.domain.diary.model.type.Sticker;
import com.ddd.tooda.util.HashtagUtil;
import com.ddd.tooda.util.OpenGraphUtil;
import com.ddd.tooda.util.OpenGraphUtil.OGResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DiaryDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateDiaryRequest {

        @NotBlank
        private String title;
        @NotBlank
        private String content;

        private Sticker sticker;

        private List<String> links = new ArrayList<>();

        private List<String> images = new ArrayList<>();

        private List<DiaryStockDto> stocks = new ArrayList<>();

        public List<String> getHashTags() {
            return HashtagUtil.findHashtags(this.content);
        }

        public Diary toEntity(Long userId) {
            List<DiaryStock> stocks = this.stocks.stream().map(DiaryStockDto::toEntity).collect(Collectors.toList());
            List<DiaryImage> images = this.images.stream().map(DiaryImage::new).collect(Collectors.toList());
            List<DiaryLink> links =
                    this.links.stream().map(OpenGraphUtil::getOgInfo)
                            .filter(Objects::nonNull).map(OGResponse::toEntity).collect(Collectors.toList());

            return Diary.builder()
                    .title(title)
                    .content(content)
                    .sticker(sticker)
                    .images(images)
                    .stocks(stocks)
                    .links(links)
                    .userId(userId)
                    .build();

        }

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DiaryStockDto {
        private Long id;
        @NotBlank
        private String name;
        @NotNull
        private float changeRate;
        @NotNull
        private ChangeType change;

        public static DiaryStockDto from(DiaryStock stock) {
            return new DiaryStockDto(stock.getId(), stock.getName(), stock.getChangeRate(), stock.getChangeType());
        }

        public DiaryStock toEntity() {
            return new DiaryStock(this.id, this.getName(), this.getChangeRate(), this.getChange(), null);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class DiaryLinkDto {

        private Long id;
        private String ogImage;
        private String ogTitle;
        private String ogDescription;
        private String ogUrl;

        public static DiaryLinkDto from(DiaryLink link) {
            return new DiaryLinkDto(link.getId(), link.getOgImage(), link.getOgTitle(), link.getOgDescription(),
                    link.getOgUrl());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class DiaryImageDto {
        private Long id;
        @NotBlank
        private String image;

        public static DiaryImageDto from(DiaryImage image) {
            return new DiaryImageDto(image.getId(), image.getImage());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class FindAllByDateRequest {
        @NotNull
        private int year;
        @NotNull
        private int month;


        public LocalDateTime getStartTime() {
            return LocalDate.of(year, month, 1).atStartOfDay().plusHours(9);
        }

        public LocalDateTime getEndTime() {
            LocalDate startDate = getStartTime().toLocalDate();
            return startDate.withDayOfMonth(startDate.lengthOfMonth()).atStartOfDay().plusHours(9);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class DiaryResponse {
        private Long id;
        private String title;
        private String content;
        private Sticker sticker;
        private List<DiaryStockDto> stocks;
        private List<DiaryImageDto> images;
        private List<DiaryLinkDto> links;

        public static DiaryResponse from(Diary diary) {
            return new DiaryResponse(diary.getId(), diary.getTitle(), diary.getContent(), diary.getSticker(),
                    diary.getStocks()
                            .stream()
                            .map(s -> DiaryStockDto.from(s))
                            .collect(Collectors.toList()),
                    diary.getImages()
                            .stream()
                            .map(v -> DiaryImageDto.from(v))
                            .collect(Collectors.toList()),
                    diary.getLinks()
                            .stream()
                            .map(s -> DiaryLinkDto.from(s))
                            .collect(Collectors.toList())
            );
        }
    }
}
