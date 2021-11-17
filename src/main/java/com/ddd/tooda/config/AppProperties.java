package com.ddd.tooda.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Component
@ConstructorBinding
@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final JwtConfig jwtConfig;

    @Getter
    @RequiredArgsConstructor
    public static class JwtConfig {
        private final String jwtAccessSecret;
        private final String jwtRefreshSecret;
        private final int jwtAccessExpireSec;
        private final int jwtRefreshExpireSec;
    }
}
