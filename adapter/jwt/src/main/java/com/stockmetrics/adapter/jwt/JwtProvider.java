package com.stockmetrics.adapter.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Date;

/**
 * JWT utility for generating and parsing HS256-signed tokens.
 * 
 * <p>Usage with Spring configuration:
 * <pre>
 * jwt:
 *   secret: your-secret-key-at-least-32-bytes
 *   expiration-seconds: 3600
 * </pre>
 */
public class JwtProvider {

    private final SecretKey secretKey;
    private final long expirationSeconds;
    private final Clock clock;

    public JwtProvider(String secret, long expirationSeconds, Clock clock) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationSeconds = expirationSeconds;
        this.clock = clock;
    }

    /**
     * Generates a JWT token for the given subject.
     *
     * @param subject the subject (e.g., user ID) to include in the token
     * @return the signed JWT token
     */
    public String generateToken(String subject) {
        Date now = Date.from(clock.instant());
        Date expiration = Date.from(clock.instant().plusSeconds(expirationSeconds));

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Parses and validates a JWT token, returning the subject.
     *
     * @param token the JWT token to parse
     * @return the subject from the token
     * @throws InvalidTokenException if the token is expired, invalid, or malformed
     */
    public String parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .clock(() -> Date.from(clock.instant()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token has expired", e);
        } catch (SignatureException e) {
            throw new InvalidTokenException("Invalid token signature", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }
}
