package com.ddd.tooda.domain.diary.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyDiaryMetaRow {
    private Long id;
    private int year;
    private int month;
    private int totalCount;
    private String sticker;
}
