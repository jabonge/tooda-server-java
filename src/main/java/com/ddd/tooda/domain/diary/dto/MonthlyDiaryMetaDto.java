package com.ddd.tooda.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MonthlyDiaryMetaDto {

    @Getter
    @ToString
    public static class MonthlyDiaryMetaResponse {
        private Long id;
        private int year;
        private int month;
        private int totalCount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime updatedAt;
        private List<String> stickers = new ArrayList<>();

        public MonthlyDiaryMetaResponse(Long id, int year, int month, int totalCount, String sticker, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.year = year;
            this.month = month;
            this.totalCount = totalCount;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
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
            return new MonthlyDiaryMetaResponse(row.getId(),row.getYear(),row.getMonth(), row.getTotalCount(), row.getSticker(), row.getCreatedAt(), row.getUpdatedAt());
        }

    }

}
