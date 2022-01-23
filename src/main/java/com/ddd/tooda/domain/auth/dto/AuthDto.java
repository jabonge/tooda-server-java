package com.ddd.tooda.domain.auth.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

public class AuthDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpResponse {
        private String accessToken;
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
    public static class AppleSignUpRequest {
        private String identityToken;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    public static class IssueAccessTokenRequest {
        private String refreshToken;
    }


}
