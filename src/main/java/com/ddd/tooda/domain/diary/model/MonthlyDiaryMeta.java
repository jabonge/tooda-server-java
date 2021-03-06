package com.ddd.tooda.domain.diary.model;

import com.ddd.tooda.common.BaseEntity;
import com.ddd.tooda.domain.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(of = {"id","year","month"},callSuper = false)
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyDiaryMeta extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int totalCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "monthlyDiaryMeta")
    private List<Diary> diaries = new ArrayList<>();

    @Builder
    public MonthlyDiaryMeta(int year, int month, Long userId) {
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
        diaries.add(diary);
        diary.setMonthlyDiaryMeta(this);
    }

    public void setUpdatedAtToNow() {
        this.updatedAt = LocalDateTime.now();
    }

}
