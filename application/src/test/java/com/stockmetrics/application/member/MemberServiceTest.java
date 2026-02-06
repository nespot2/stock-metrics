package com.stockmetrics.application.member;

import com.stockmetrics.application.required.member.MemberRepository;
import com.stockmetrics.domain.member.CreateMemberRequest;
import com.stockmetrics.domain.member.Member;
import com.stockmetrics.domain.member.MemberNotFoundException;
import com.stockmetrics.domain.member.MemberStatus;
import com.stockmetrics.domain.member.SnsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("Should reject invalid email format")
    void shouldRejectInvalidEmailFormat() {
        // given
        RegisterMemberCommand command = new RegisterMemberCommand("invalid-email", "John Doe", SnsType.EMAIL, "password123");

        // when & then
        assertThatThrownBy(() -> memberService.register(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("email");

        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should reject if member already exists")
    void shouldRejectIfMemberAlreadyExists() {
        // given
        String email = "john@example.com";
        RegisterMemberCommand command = new RegisterMemberCommand(email, "John Doe", SnsType.EMAIL, "password123");
        Member existingMember = Member.create(new CreateMemberRequest(email, "Existing User", SnsType.EMAIL, "password123"));
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(existingMember));

        // when & then
        assertThatThrownBy(() -> memberService.register(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already exists");

        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should register a member successfully")
    void shouldRegisterMember() {
        // given
        String email = "john@example.com";
        String name = "John Doe";
        RegisterMemberCommand command = new RegisterMemberCommand(email, name, SnsType.EMAIL, "password123");

        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Member result = memberService.register(command);

        // then
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getName()).isEqualTo(name);
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("Should modify member name using service")
    void shouldModifyMemberName() {
        // given
        String email = "john@example.com";
        String originalName = "John Doe";
        String newName = "John Smith";
        Member existingMember = Member.create(new CreateMemberRequest(email, originalName, SnsType.EMAIL, "password123"));
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(existingMember));

        UpdateMemberNameCommand command = new UpdateMemberNameCommand(email, newName);

        // when
        Member result = memberService.updateName(command);

        // then
        assertThat(result.getName()).isEqualTo(newName);
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Should reject update if member does not exist")
    void shouldRejectUpdateIfMemberDoesNotExist() {
        // given
        String email = "john@example.com";
        String newName = "John Smith";
        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());

        UpdateMemberNameCommand command = new UpdateMemberNameCommand(email, newName);

        // when & then
        assertThatThrownBy(() -> memberService.updateName(command))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("not found");
    }

    @Test
    @DisplayName("Should delete a member")
    void shouldDeleteMember() {
        // given
        Long memberId = 1L;
        Member member = Member.create(new CreateMemberRequest("john@example.com", "John Doe", SnsType.EMAIL, "password123"));
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        // when
        memberService.delete(memberId);

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DELETED);
    }

    @Test
    @DisplayName("Should throw MemberNotFoundException when not finding a member")
    void shouldThrowMemberNotFoundExceptionWhenNotFindingMember() {
        // given
        Long memberId = 1L;
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberService.delete(memberId))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("Should throw MemberNotFoundException when MemberStatus is DELETED")
    void shouldThrowMemberNotFoundExceptionWhenMemberStatusIsDeleted() {
        // given
        Long memberId = 1L;
        Member member = Member.create(new CreateMemberRequest("john@example.com", "John Doe", SnsType.EMAIL, "password123"));
        member.delete(); // Member status is now DELETED
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        // when & then
        assertThatThrownBy(() -> memberService.delete(memberId))
                .isInstanceOf(MemberNotFoundException.class);
    }
}
