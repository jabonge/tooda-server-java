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
        this.jwtAccessSecret = appProperties.getJwtConfig().getJwtAccessSecret();
        this.jwtRefreshSecret = appProperties.getJwtConfig().getJwtRefreshSecret();
        this.jwtAccessExpireMillisecond = appProperties.getJwtConfig().getJwtAccessExpireSec() * 1000;
        this.jwtRefreshExpireMillisecond = appProperties.getJwtConfig().getJwtRefreshExpireSec() * 1000;
    }

    private String createToken(Long userId, int expireTime, String secretKey) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireTime);

        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    private boolean validateToken(String token,String secretKey) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }


    public String createAccessToken(Long userId) {
        return createToken(userId,jwtAccessExpireMillisecond,jwtAccessSecret);
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, jwtRefreshExpireMillisecond, jwtRefreshSecret);
    }


    public boolean validateAccessToken(String token) {
        return validateToken(token,jwtAccessSecret);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token,jwtRefreshSecret);
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtAccessSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}
