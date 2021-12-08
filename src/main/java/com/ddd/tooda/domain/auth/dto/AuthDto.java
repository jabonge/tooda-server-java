package com.ddd.tooda.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

public class AuthDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpResponse {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccessTokenResponse {
        private String accessToken;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class SignUpRequest {
        @Length(min = 36, max = 40, message = "올바른 DeviceId가 아닙니다.")
        private String deviceId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class IssueAccessTokenRequest {
        private String refreshToken;
    }


}
