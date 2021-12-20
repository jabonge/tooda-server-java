package com.ddd.tooda.domain.diary.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyDiaryMetaRow {
    private Long id;
    private Integer year;
    private Integer month;
    private Integer totalCount;
    private String sticker;
}
