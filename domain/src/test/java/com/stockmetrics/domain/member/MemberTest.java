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
        CreateMemberRequest request = new CreateMemberRequest("test@example.com", "John Doe", SnsType.EMAIL, "password123");

        // when
        Member member = Member.create(request);

        // then
        assertThat(member.getEmail()).isEqualTo("test@example.com");
        assertThat(member.getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Should reject blank name on member creation")
    void shouldRejectBlankNameOnMemberCreation() {
        // given
        CreateMemberRequest request = new CreateMemberRequest("test@example.com", " ", SnsType.EMAIL, "password123");

        // when & then
        assertThatThrownBy(() -> Member.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "test@", "@example.com", "test @example.com", "test@.com", ""})
    @DisplayName("Should reject email not following format")
    void shouldRejectInvalidEmail(String invalidEmail) {
        // given
        CreateMemberRequest request = new CreateMemberRequest(invalidEmail, "John Doe", SnsType.EMAIL, "password123");

        // when & then
        assertThatThrownBy(() -> Member.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("email");
    }

    @Test
    @DisplayName("Should modify a name in Member entity")
    void shouldModifyName() {
        // given
        Member member = MemberFixture.createMember();

        // when
        member.modifyName("New Name");

        // then
        assertThat(member.getName()).isEqualTo("New Name");
    }

    @Test
    @DisplayName("Should delete a member by changing status to DELETED")
    void shouldDeleteMember() {
        // given
        Member member = MemberFixture.createMember();

        // when
        member.delete();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DELETED);
    }

    @Test
    @DisplayName("Should create an EMAIL type member with password")
    void shouldCreateEmailTypeMemberWithPassword() {
        // given
        CreateMemberRequest request = new CreateMemberRequest("test@example.com", "John Doe", SnsType.EMAIL, "password123");

        // when
        Member member = Member.create(request);

        // then
        assertThat(member.getEmail()).isEqualTo("test@example.com");
        assertThat(member.getName()).isEqualTo("John Doe");
        assertThat(member.getSnsType()).isEqualTo(SnsType.EMAIL);
        assertThat(member.getPassword()).isEqualTo("password123");
    }

    @Test
    @DisplayName("Should reject EMAIL type member without password")
    void shouldRejectEmailTypeMemberWithoutPassword() {
        // given
        CreateMemberRequest request = new CreateMemberRequest("test@example.com", "John Doe", SnsType.EMAIL, null);

        // when & then
        assertThatThrownBy(() -> Member.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password is required for EMAIL type member");
    }

    @Test
    @DisplayName("Should reject EMAIL type member with empty password")
    void shouldRejectEmailTypeMemberWithEmptyPassword() {
        // given
        CreateMemberRequest request = new CreateMemberRequest("test@example.com", "John Doe", SnsType.EMAIL, "");

        // when & then
        assertThatThrownBy(() -> Member.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password is required for EMAIL type member");
    }

    @Test
    @DisplayName("Should create a NAVER type member without password")
    void shouldCreateNaverTypeMemberWithoutPassword() {
        // given
        CreateMemberRequest request = new CreateMemberRequest("test@example.com", "John Doe", SnsType.NAVER, null);

        // when
        Member member = Member.create(request);

        // then
        assertThat(member.getEmail()).isEqualTo("test@example.com");
        assertThat(member.getName()).isEqualTo("John Doe");
        assertThat(member.getSnsType()).isEqualTo(SnsType.NAVER);
        assertThat(member.getPassword()).isNull();
    }

    @Test
    @DisplayName("Should throw MemberNotFoundException when deleting already deleted member")
    void shouldThrowExceptionWhenDeletingDeletedMember() {
        // given
        Member member = MemberFixture.createMember();
        member.delete();

        // when & then
        assertThatThrownBy(() -> member.delete())
                .isInstanceOf(MemberNotFoundException.class);
    }

}
