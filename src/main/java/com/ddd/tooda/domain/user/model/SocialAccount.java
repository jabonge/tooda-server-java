package com.ddd.tooda.domain.user.model;

import com.ddd.tooda.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "ix_social_account_oauth_id", columnList = "socialId"),
        @Index(name = "ix_social_account_oauth_name", columnList = "socialName")
})
public class SocialAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column
    private SocialName socialName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public SocialAccount(String socialId, SocialName socialName) {
        this.socialId = socialId;
        this.socialName = socialName;
    }

    void setUser(User user) {
        this.user = user;
    }
}
