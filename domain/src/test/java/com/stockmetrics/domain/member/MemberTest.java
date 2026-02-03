package com.stockmetrics.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

}
