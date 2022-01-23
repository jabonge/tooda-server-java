package com.ddd.tooda.domain.user.model;

import java.util.Arrays;

public enum SocialName {
    APPLE;

    public static SocialName of(String socialName) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(socialName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 인증타입니다."));
    }

}
