package com.ddd.tooda.domain.user.model;

import com.ddd.tooda.common.BaseEntity;
import com.ddd.tooda.domain.diary.model.Diary;
import com.ddd.tooda.domain.diary.model.MonthlyDiaryMeta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST}, mappedBy = "user", orphanRemoval = true)
    private SocialAccount socialAccount;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private final List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private final List<MonthlyDiaryMeta> monthlyDiaryMetas = new ArrayList<>();

    public User(Long id) {
        this.id = id;
    }

    public User(SocialAccount socialAccount) {
        this.socialAccount = socialAccount;
        socialAccount.setUser(this);
    }

}
