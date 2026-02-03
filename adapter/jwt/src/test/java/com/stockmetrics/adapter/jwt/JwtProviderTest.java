package com.stockmetrics.adapter.jwt;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtProviderTest {

    private static final String SECRET = "test-secret-key-that-is-at-least-32-bytes-long";
    private static final long EXPIRATION_SECONDS = 3600L;

    private final Clock fixedClock = Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"), ZoneId.of("UTC"));
    private final JwtProvider jwtProvider = new JwtProvider(SECRET, EXPIRATION_SECONDS, fixedClock);

    @Test
    void shouldGenerateTokenForSubject() {
        String subject = "user123";

        String token = jwtProvider.generateToken(subject);

        assertThat(token).isNotBlank();
    }

    @Test
    void shouldParseValidTokenAndReturnSubject() {
        String subject = "user123";
        String token = jwtProvider.generateToken(subject);

        String parsedSubject = jwtProvider.parseToken(token);

        assertThat(parsedSubject).isEqualTo(subject);
    }

    @Test
    void shouldRejectExpiredToken() {
        String subject = "user123";
        String token = jwtProvider.generateToken(subject);

        Clock expiredClock = Clock.fixed(
                Instant.parse("2026-01-01T00:00:00Z").plusSeconds(EXPIRATION_SECONDS + 1),
                ZoneId.of("UTC")
        );
        JwtProvider laterProvider = new JwtProvider(SECRET, EXPIRATION_SECONDS, expiredClock);

        assertThatThrownBy(() -> laterProvider.parseToken(token))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessageContaining("expired");
    }

    @Test
    void shouldRejectTokenWithInvalidSignature() {
        String subject = "user123";
        String token = jwtProvider.generateToken(subject);

        String differentSecret = "different-secret-key-that-is-at-least-32-bytes";
        JwtProvider differentProvider = new JwtProvider(differentSecret, EXPIRATION_SECONDS, fixedClock);

        assertThatThrownBy(() -> differentProvider.parseToken(token))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessageContaining("Invalid");
    }

    @Test
    void shouldRejectBlankToken() {
        assertThatThrownBy(() -> jwtProvider.parseToken(""))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessageContaining("Invalid");
    }

    @Test
    void shouldRejectMalformedToken() {
        assertThatThrownBy(() -> jwtProvider.parseToken("not.a.valid.token"))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessageContaining("Invalid");
    }
}
