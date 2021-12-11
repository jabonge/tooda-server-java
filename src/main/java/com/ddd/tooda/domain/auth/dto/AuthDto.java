package com.ddd.tooda.domain.auth.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

public class AuthDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpResponse {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AccessTokenResponse {
        private String accessToken;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    public static class SignUpRequest {
        @Length(min = 36, max = 40, message = "올바른 DeviceId가 아닙니다.")
        private String deviceId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    public static class IssueAccessTokenRequest {
        private String refreshToken;
    }


}
