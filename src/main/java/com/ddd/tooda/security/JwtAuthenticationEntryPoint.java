package com.ddd.tooda.security;

import com.ddd.tooda.common.exception.UnAuthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
      log.error("Unauthorized Error : {}", authException.getMessage());
//      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        OutputStream os = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(os, new UnAuthorizedException(authException.getMessage()));
        os.flush();
    }
}
