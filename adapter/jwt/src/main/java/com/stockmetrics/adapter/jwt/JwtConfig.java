package com.stockmetrics.adapter.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    @Bean
    public JwtProvider jwtProvider(JwtProperties properties) {
        return new JwtProvider(
                properties.secret(),
                properties.expirationSeconds(),
                Clock.systemUTC()
        );
    }
}
