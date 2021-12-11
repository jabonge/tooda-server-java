package com.ddd.tooda.domain.diary.dto;

import com.ddd.tooda.domain.diary.model.type.ChangeType;
import com.ddd.tooda.domain.diary.model.type.Sticker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DiaryDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateDiaryRequest {

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class DiaryStock {
            @NotBlank
            private String name;

            @NotNull
            private float changeRate;

            @NotNull
            private ChangeType changeType;

        }
        @NotBlank
        private String title;
        @NotBlank
        private String content;

        private Sticker sticker;

        private List<String> links;

        private List<String> images;

        private List<DiaryStock> stocks;


    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DiaryResponse {

    }
}
