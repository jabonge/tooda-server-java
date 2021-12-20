package com.ddd.tooda.domain.diary.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class MonthlyDiaryMetaDto {

    @Getter
    @ToString
    public static class MonthlyDiaryMetaResponse {
        private Long id;
        private Integer year;
        private Integer month;
        private Integer totalCount;
        private List<String> stickers = new ArrayList<>();

        public MonthlyDiaryMetaResponse(Long id, int year, int month, int totalCount, String sticker) {
            this.id = id;
            this.year = year;
            this.month = month;
            this.totalCount = totalCount;
            this.stickers.add(sticker);
        }

        public static List<MonthlyDiaryMetaResponse> fromRows(List<MonthlyDiaryMetaRow> rows) {
            if (rows.isEmpty()) return new ArrayList<>();
            MonthlyDiaryMetaResponse first = MonthlyDiaryMetaResponse.fromRow(rows.remove(0));
            List<MonthlyDiaryMetaResponse> responses = new ArrayList<>(List.of(first));
            rows.forEach(e -> {
                MonthlyDiaryMetaResponse last = responses.get(responses.size() - 1);
                if (e.getId().equals(last.getId())) {
                    last.getStickers().add(e.getSticker());
                } else {
                    responses.add(MonthlyDiaryMetaResponse.fromRow(e));
                }
            });
            return responses;
        }

        private static MonthlyDiaryMetaResponse fromRow(MonthlyDiaryMetaRow row) {
            return new MonthlyDiaryMetaResponse(row.getId(),row.getYear(),row.getMonth(), row.getTotalCount(), row.getSticker());
        }

    }

}
