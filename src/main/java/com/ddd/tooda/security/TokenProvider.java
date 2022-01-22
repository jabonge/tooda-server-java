package com.ddd.tooda.security;

import com.ddd.tooda.config.AppProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private final String jwtAccessSecret;
    private final String jwtRefreshSecret;
    private final int jwtAccessExpireMillisecond;
    private final int jwtRefreshExpireMillisecond;


    public TokenProvider(AppProperties appProperties) {
        this.jwtAccessSecret = appProperties.getJwtConfig().getAccessSecret();
        this.jwtRefreshSecret = appProperties.getJwtConfig().getRefreshSecret();
        this.jwtAccessExpireMillisecond = appProperties.getJwtConfig().getAccessExpireSec() * 1000;
        this.jwtRefreshExpireMillisecond = appProperties.getJwtConfig().getRefreshExpireSec() * 1000;
    }

    private String createToken(Long userId, int expireTime, String secretKey) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireTime);

        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(now)
//                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    private Long getUserIdInToken(String token, String secretKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());

    }

    public String createAccessToken(Long userId) {
        return createToken(userId,jwtAccessExpireMillisecond,jwtAccessSecret);
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, jwtRefreshExpireMillisecond, jwtRefreshSecret);
    }


    public Long getUserIdInAccessToken(String token) {
        return getUserIdInToken(token,jwtAccessSecret);
    }

    public Long getUserIdInRefreshToken(String token) {
        return getUserIdInToken(token,jwtRefreshSecret);
    }

}
