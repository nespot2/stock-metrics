package com.stockmetrics.bootstrap.api.member.docs;

import com.stockmetrics.adapter.web.exception.GlobalExceptionHandler;
import com.stockmetrics.adapter.web.member.MemberController;
import com.stockmetrics.application.member.RegisterMemberCommand;
import com.stockmetrics.application.provided.member.MemberRegistrationUseCase;
import com.stockmetrics.domain.member.CreateMemberRequest;
import com.stockmetrics.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restdocs.test.autoconfigure.AutoConfigureRestDocs;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({MemberController.class, GlobalExceptionHandler.class})
@AutoConfigureRestDocs
class MemberApiDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberRegistrationUseCase memberRegistrationUseCase;

    @Test
    void documentMemberRegistration() throws Exception {
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
                .andDo(document("member-registration",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("회원 이메일").attributes(key("required").value("Yes")),
                                fieldWithPath("name").description("회원 이름").attributes(key("required").value("Yes"))
                        ),
                        responseFields(
                                fieldWithPath("email").description("회원 이메일").attributes(key("required").value("Yes")),
                                fieldWithPath("name").description("회원 이름").attributes(key("required").value("Yes"))
                        )
                ));
    }
}
