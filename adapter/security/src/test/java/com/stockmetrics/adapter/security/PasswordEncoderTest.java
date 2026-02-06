package com.stockmetrics.adapter.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordEncoderTest {

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Test
    void shouldEncodeRawPassword() {
        String rawPassword = "mySecret123";

        String encoded = passwordEncoder.encode(rawPassword);

        assertThat(encoded).isNotBlank();
        assertThat(encoded).isNotEqualTo(rawPassword);
    }

    @Test
    void shouldReturnTrueWhenRawPasswordMatchesEncodedPassword() {
        String rawPassword = "mySecret123";
        String encoded = passwordEncoder.encode(rawPassword);

        boolean matches = passwordEncoder.matches(rawPassword, encoded);

        assertThat(matches).isTrue();
    }

    @Test
    void shouldReturnFalseWhenRawPasswordDoesNotMatchEncodedPassword() {
        String rawPassword = "mySecret123";
        String wrongPassword = "wrongPassword";
        String encoded = passwordEncoder.encode(rawPassword);

        boolean matches = passwordEncoder.matches(wrongPassword, encoded);

        assertThat(matches).isFalse();
    }
}
