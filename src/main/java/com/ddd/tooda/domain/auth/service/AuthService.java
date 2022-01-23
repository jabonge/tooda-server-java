package com.ddd.tooda.domain.auth.service;

import com.ddd.tooda.domain.auth.dto.AuthDto;
import com.ddd.tooda.domain.auth.oauth.apple.AppleService;
import com.ddd.tooda.domain.user.model.SocialName;
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
    private final AppleService appleService;

    public AuthDto.SignUpResponse appleSignUpOrLogin(AuthDto.AppleSignUpRequest request) {
        String socialId = appleService.getClientId(request.getIdentityToken());
        User user = userService.signUpOrLogin(SocialName.APPLE, socialId);
        String accessToken = tokenProvider.createAccessToken(user.getId());
        return new AuthDto.SignUpResponse(accessToken);
    }

    public AuthDto.AccessTokenResponse reIssueAccessToken(AuthDto.IssueAccessTokenRequest request) {
        Long userId = tokenProvider.getUserIdInRefreshToken(request.getRefreshToken());
        String accessToken = tokenProvider.createAccessToken(userId);

        return new AuthDto.AccessTokenResponse(accessToken);

    }
}
