package com.stockmetrics.bootstrap.api.member;

import com.stockmetrics.adapter.web.exception.GlobalExceptionHandler;
import com.stockmetrics.adapter.web.member.MemberController;
import com.stockmetrics.application.member.RegisterMemberCommand;
import com.stockmetrics.application.provided.member.MemberRegistrationUseCase;
import com.stockmetrics.domain.member.CreateMemberRequest;
import com.stockmetrics.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({MemberController.class, GlobalExceptionHandler.class})
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberRegistrationUseCase memberRegistrationUseCase;

    @Test
    void shouldReturn201CreatedOnSuccessfulMemberRegistration() throws Exception {
        // given
        String requestBody = """
                {
                    "email": "john@example.com",
                    "name": "John Doe"
                }
                """;

        given(memberRegistrationUseCase.register(any(RegisterMemberCommand.class)))
                .willReturn(Member.create(new CreateMemberRequest("john@example.com", "John Doe")));

        // when & then
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void shouldReturn400BadRequestOnInvalidEmailFormat() throws Exception {
        // given
        String requestBody = """
                {
                    "email": "invalid-email",
                    "name": "John Doe"
                }
                """;

        willThrow(new IllegalArgumentException("Invalid email format"))
                .given(memberRegistrationUseCase).register(any(RegisterMemberCommand.class));

        // when & then
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid email format"));
    }
}
