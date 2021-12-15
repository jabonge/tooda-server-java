package com.ddd.tooda.domain.diary.model;

import com.ddd.tooda.common.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiaryLink extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String ogImage;

    @Column
    String ogTitle;

    @Column
    String ogDescription;

    @Column(nullable = false)
    String ogUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    Diary diary;

    void setDiary(Diary diary){
        this.diary = diary;
    }

}
