package com.ddd.tooda.domain.auth.service;

import com.ddd.tooda.domain.auth.dto.AuthDto;
import com.ddd.tooda.domain.user.model.User;
import com.ddd.tooda.domain.user.service.UserService;
import com.ddd.tooda.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    public AuthDto.SignUpResponse signUp(AuthDto.SignUpRequest request) {
        User user = userService.signUp(request.getDeviceId());
        String accessToken = tokenProvider.createAccessToken(user.getId());
        String refreshToken = tokenProvider.createRefreshToken(user.getId());

        return new AuthDto.SignUpResponse(accessToken,refreshToken);
    }

    public AuthDto.AccessTokenResponse reIssueAccessToken(AuthDto.IssueAccessTokenRequest request) {
        Long userId = tokenProvider.getUserIdInRefreshToken(request.getRefreshToken());
        String accessToken = tokenProvider.createAccessToken(userId);

        return new AuthDto.AccessTokenResponse(accessToken);

    }
}
