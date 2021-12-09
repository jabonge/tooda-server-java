package com.ddd.tooda.domain.diary.model;

import com.ddd.tooda.common.BaseEntity;
import com.ddd.tooda.domain.diary.model.type.Sticker;
import com.ddd.tooda.domain.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "ix_diary_title", columnList = "title"),
        @Index(name = "ix_diary_createdAt", columnList = "createdAt")
})
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private Sticker sticker;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
    private User user;

    @OneToMany(cascade = { CascadeType.ALL },mappedBy = "diary")
    private List<DiaryImage> images = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "diary")
    private List<DiaryLink> links = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "diary")
    private List<DiaryStock> stocks = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "diary_hashtag",
        joinColumns= @JoinColumn(name = "diary_id"),
        inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<HashTag> hashTags = new ArrayList<>();








}
