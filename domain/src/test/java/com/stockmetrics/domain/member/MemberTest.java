package com.stockmetrics.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    @DisplayName("Should create a Member with email and name using request class")
    void shouldCreateMemberWithEmailAndName() {
        // given
        CreateMemberRequest request = new CreateMemberRequest("test@example.com", "John Doe");

        // when
        Member member = Member.create(request);

        // then
        assertThat(member.getEmail()).isEqualTo("test@example.com");
        assertThat(member.getName()).isEqualTo("John Doe");
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "test@", "@example.com", "test @example.com", "test@.com", ""})
    @DisplayName("Should reject email not following format")
    void shouldRejectInvalidEmail(String invalidEmail) {
        // given
        CreateMemberRequest request = new CreateMemberRequest(invalidEmail, "John Doe");

        // when & then
        assertThatThrownBy(() -> Member.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("email");
    }

}
