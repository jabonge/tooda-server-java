package com.ddd.tooda.domain.auth.controller;

import com.ddd.tooda.domain.auth.dto.AuthDto;
import com.ddd.tooda.domain.auth.service.AuthService;
import com.ddd.tooda.security.JwtAuthenticationEntryPoint;
import com.ddd.tooda.security.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("회원가입 올바른 deviceId")
    void test_1() throws Exception {
        String deviceId = "4C792DD3-6F7A-4B9C-9621-F1FA4579F57F";
        String accessToken = "access_token";
        String refreshToken = "refresh_token";

        AuthDto.SignUpRequest request = new AuthDto.SignUpRequest(deviceId);
        AuthDto.SignUpResponse response = new AuthDto.SignUpResponse(accessToken,refreshToken);
        Mockito.doReturn(response).when(authService).signUp(request);


         mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(accessToken))
                .andExpect(jsonPath("$.refreshToken").value(refreshToken))
                .andDo(print());

    }



}