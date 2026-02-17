package com.stockmetrics.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SnsTypeTest {

    @Test
    @DisplayName("Should contain only KAKAO and NAVER sns types")
    void shouldContainOnlyKakaoAndNaverSnsTypes() {
        assertThat(SnsType.values()).containsExactly(SnsType.KAKAO, SnsType.NAVER);
    }
}
