package com.ddd.tooda.domain.user.model;

import com.ddd.tooda.common.BaseEntity;
import lombok.*;

import javax.persistence.*;


@Getter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "ux_user_deviceId", unique = true, columnList = "deviceId")
})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deviceId;

    @Builder
    public User(String deviceId) {
        this.deviceId = deviceId;
        this.id = id;
    }


}
