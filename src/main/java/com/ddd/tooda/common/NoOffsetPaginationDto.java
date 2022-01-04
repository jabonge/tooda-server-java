package com.ddd.tooda.common;


import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NoOffsetPaginationDto {
    private Long cursor;
    @Max(20)
    @Min(1)
    private int limit = 20;
}
