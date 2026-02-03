package com.stockmetrics.adapter.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secret,
        long expirationSeconds
) {
    public JwtProperties {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT secret must not be blank");
        }
        if (expirationSeconds <= 0) {
            throw new IllegalArgumentException("JWT expiration must be positive");
        }
    }
}
