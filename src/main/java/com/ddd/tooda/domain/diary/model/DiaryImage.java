package com.ddd.tooda.domain.diary.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    Diary diary;

    public DiaryImage(String image) {
        this.image = image;
    }

    void setDiary(Diary diary){
        this.diary = diary;
    }


}
