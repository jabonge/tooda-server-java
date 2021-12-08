package com.ddd.tooda.domain.auth.controller;


import com.ddd.tooda.domain.auth.dto.AuthDto;
import com.ddd.tooda.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseBody
    ResponseEntity<AuthDto.SignUpResponse> signUp(@Valid @RequestBody AuthDto.SignUpRequest request) {
        AuthDto.SignUpResponse response = authService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    ResponseEntity<AuthDto.AccessTokenResponse> reIssueAccessToken(@RequestBody AuthDto.IssueAccessTokenRequest request) {
        AuthDto.AccessTokenResponse response = authService.reIssueAccessToken(request);
        return ResponseEntity.ok(response);
    }

}
