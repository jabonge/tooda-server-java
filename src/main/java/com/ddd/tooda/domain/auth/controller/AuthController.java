package com.ddd.tooda.domain.auth.controller;


import com.ddd.tooda.domain.auth.dto.AuthDto;
import com.ddd.tooda.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(AuthController.AUTH)
@RequiredArgsConstructor
public class AuthController {

    public static final String AUTH = "/auth";
    private final AuthService authService;

    @PostMapping("/sign-up/apple")
    @ResponseBody
    ResponseEntity<AuthDto.SignUpResponse> appleSignUpOrLogin(@Valid @RequestBody AuthDto.AppleSignUpRequest request) {
        AuthDto.SignUpResponse response = authService.appleSignUpOrLogin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    ResponseEntity<AuthDto.AccessTokenResponse> reIssueAccessToken(@RequestBody AuthDto.IssueAccessTokenRequest request) {
        AuthDto.AccessTokenResponse response = authService.reIssueAccessToken(request);
        return ResponseEntity.ok(response);
    }

}
