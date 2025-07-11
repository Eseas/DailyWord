package com.dailyword.auth.application.service;

import com.dailyword.auth.config.AuthProperties;
import com.dailyword.auth.dto.TokenResponse;
import com.dailyword.auth.exception.InvalidRefreshTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final AuthProperties jwtProperties;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public TokenResponse generateToken(String memberRefCode) {
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(memberRefCode)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessTokenExpirationMs()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()), SIGNATURE_ALGORITHM)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(memberRefCode)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshTokenExpirationMs()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()), SIGNATURE_ALGORITHM)
                .compact();

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(jwtProperties.getAccessTokenExpirationMs())
                .refreshTokenExpiresIn(jwtProperties.getRefreshTokenExpirationMs())
                .build();
    }

    public Claims validateTokenAndGetClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new InvalidRefreshTokenException("Invalid refresh token", e);
        }
    }
}