package com.ddd.tooda.domain.diary.model;

import com.ddd.tooda.common.BaseEntity;
import com.ddd.tooda.domain.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyDiaryMeta extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer year;

    @Column
    private Integer month;

    @Column
    private Integer totalCount = 1;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "monthlyDiaryMeta", cascade = {CascadeType.PERSIST})
    private List<Diary> diarys = new ArrayList<>();

    @Builder
    public MonthlyDiaryMeta(Integer year, Integer month, Long userId) {
        this.year = year;
        this.month = month;
        this.user = new User(userId);
    }

    public void increaseTotalCount() {
        this.totalCount++;
    }
    public void decreaseTotalCount() {
        if (this.totalCount > 0) {
            this.totalCount--;
        }
    }

    public void addDiary(Diary diary) {
        diarys.add(diary);
        diary.setMonthlyDiaryMeta(this);
    }

}
