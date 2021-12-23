package com.ddd.tooda.domain.auth.service;

import com.ddd.tooda.domain.auth.dto.AuthDto;
import com.ddd.tooda.domain.user.model.User;
import com.ddd.tooda.domain.user.service.UserService;
import com.ddd.tooda.security.TokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthService authService;


    @Test
    @DisplayName("회원가입 응답 테스트")
    void test_1() {
        String deviceId = "test-device-id";
        User user = new User(1L, deviceId);
        doReturn(user).when(userService).signUp(deviceId);

        String accessToken = "access_token";
        String refreshToken = "refresh_token";
        doReturn(accessToken).when(tokenProvider).createAccessToken(user.getId());
        doReturn(refreshToken).when(tokenProvider).createRefreshToken(user.getId());

        AuthDto.SignUpRequest request = new AuthDto.SignUpRequest(deviceId);
        AuthDto.SignUpResponse response = authService.signUp(request);

        assertEquals(response.getAccessToken(),accessToken);
        assertNotNull(response.getRefreshToken(),refreshToken);
    }

    @Test
    @DisplayName("AccessToken 재발급")
    void test_2() {

        String accessToken = "access_token";
        String refreshToken = "refresh_token";

        Long userId = 1L;

        doReturn(userId).when(tokenProvider).getUserIdInRefreshToken(refreshToken);
        doReturn(accessToken).when(tokenProvider).createAccessToken(userId);


        AuthDto.IssueAccessTokenRequest request = new AuthDto.IssueAccessTokenRequest(refreshToken);
        AuthDto.AccessTokenResponse response = authService.reIssueAccessToken(request);

        assertEquals(response.getAccessToken(),accessToken);
    }


}