package com.ddd.tooda.domain.diary.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MonthlyDiaryMetaRow {
    private Long id;
    private int year;
    private int month;
    private int totalCount;
    private String sticker;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
