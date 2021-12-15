package com.ddd.tooda.domain.diary.model;

import com.ddd.tooda.common.BaseEntity;
import com.ddd.tooda.domain.diary.model.type.Sticker;
import com.ddd.tooda.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(name = "ix_diary_title", columnList = "title"), @Index(name = "ix_diary_createdAt",
        columnList = "createdAt")})
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "diary", orphanRemoval = true)
    private List<DiaryImage> images = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "diary", orphanRemoval = true)
    private List<DiaryLink> links = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "diary", orphanRemoval = true)
    private List<DiaryStock> stocks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_diary_meta_id")
    private MonthlyDiaryMeta monthlyDiaryMeta;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "diary_hashtag", joinColumns = @JoinColumn(name = "diary_id"), inverseJoinColumns =
    @JoinColumn(name = "hashtag_id"))
    private List<HashTag> hashTags = new ArrayList<>();

    @Builder
    public Diary(String title, String content, Sticker sticker, List<DiaryImage> images, List<DiaryStock> stocks,
                 List<DiaryLink> links, List<HashTag> hashtags, Long userId) {
        this.title = title;
        this.content = content;
        this.sticker = sticker;
        this.user = new User(userId);
        setImages(images);
        setStocks(stocks);
        setLinks(links);
    }

    public void setMonthlyDiaryMeta(MonthlyDiaryMeta monthlyDiaryMeta){
        this.monthlyDiaryMeta = monthlyDiaryMeta;
    }

    private void setImages(List<DiaryImage> images) {
        images.stream().forEach(v -> v.setDiary(this));
        this.images = images;
    }

    private void setStocks(List<DiaryStock> stocks) {
        stocks.stream().forEach(v -> v.setDiary(this));
        this.stocks = stocks;
    }

    private void setLinks(List<DiaryLink> links) {
        links.stream().forEach(v -> v.setDiary(this));
        this.links = links;
    }


}
