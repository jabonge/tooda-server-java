package com.ddd.tooda.config;

import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;


@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final JwtConfig jwtConfig;

    @Getter
    @RequiredArgsConstructor
    public static class JwtConfig {
        private final String accessSecret;
        private final String refreshSecret;
        private final int accessExpireSec;
        private final int refreshExpireSec;
    }
}
