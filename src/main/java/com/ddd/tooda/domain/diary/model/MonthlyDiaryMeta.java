package com.ddd.tooda.domain.diary.model;

import com.ddd.tooda.common.BaseEntity;
import com.ddd.tooda.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(columnDefinition = "UNSIGNED INT")
    private Integer year;

    @Column(columnDefinition = "UNSIGNED INT")
    private Integer month;

    @Column(columnDefinition = "UNSIGNED INT")
    private Integer totalCount = 0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private User user;

    @OneToMany(mappedBy = "monthlyDiaryMeta", cascade = { CascadeType.PERSIST })
    List<Diary> diarys = new ArrayList<>();

}
