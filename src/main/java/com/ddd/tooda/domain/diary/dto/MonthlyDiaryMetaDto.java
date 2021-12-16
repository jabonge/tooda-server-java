package com.ddd.tooda.domain.diary.dto;

import com.ddd.tooda.domain.diary.model.MonthlyDiaryMeta;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MonthlyDiaryMetaDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MonthlyDiaryMetaResponse {
        private Integer year;
        private Integer month;
        private Integer totalCount;
        private List<String> stickers = new ArrayList<>();

        public MonthlyDiaryMetaResponse(MonthlyDiaryMeta meta) {
            this.year = meta.getYear();
            this.month = meta.getMonth();
            this.totalCount = meta.getTotalCount();
            if (!meta.getDiaries().isEmpty()) {
                this.stickers = meta.getDiaries().stream().map(
                        diary -> diary.getSticker().getName()
                ).collect(Collectors.toList());
            }
        }

    }

}
