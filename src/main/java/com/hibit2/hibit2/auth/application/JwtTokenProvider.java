package com.hibit2.hibit2.auth.application;

import com.hibit2.hibit2.auth.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {
    private final SecretKey key;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.access.expire-length}") final long accessTokenValidityInMilliseconds,
                            @Value("${security.jwt.token.refresh.expire-length}") final long refreshTokenValidityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }


    @Override
    public String createAccessToken(final String payload) {
        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    @Override
    public String createRefreshToken(final String payload) {
        return createToken(payload, refreshTokenValidityInMilliseconds);
    }

    public String createToken(final String payload, final Long validityInMilliseconds) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setSubject(payload)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String getPayload(final String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public void validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

            claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }
}
