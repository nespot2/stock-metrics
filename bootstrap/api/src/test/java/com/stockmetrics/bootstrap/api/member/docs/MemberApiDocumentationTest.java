package com.stockmetrics.bootstrap.api.member.docs;

import com.stockmetrics.adapter.web.exception.GlobalExceptionHandler;
import com.stockmetrics.adapter.web.member.MemberController;
import com.stockmetrics.application.member.RegisterMemberCommand;
import com.stockmetrics.application.provided.member.MemberDeleteUseCase;
import com.stockmetrics.application.provided.member.MemberRegistrationUseCase;
import com.stockmetrics.domain.member.CreateMemberRequest;
import com.stockmetrics.domain.member.Member;
import com.stockmetrics.domain.member.SnsType;
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
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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

    @MockitoBean
    private MemberDeleteUseCase memberDeleteUseCase;

    @Test
    void documentMemberRegistration() throws Exception {
        // given
        String requestBody = """
                {
                    "email": "john@example.com",
                    "name": "John Doe",
                    "snsType": "EMAIL",
                    "password": "password123"
                }
                """;

        given(memberRegistrationUseCase.register(any(RegisterMemberCommand.class)))
                .willReturn(Member.create(new CreateMemberRequest("john@example.com", "John Doe", SnsType.EMAIL, "password123")));

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
                                fieldWithPath("name").description("회원 이름").attributes(key("required").value("Yes")),
                                fieldWithPath("snsType").description("SNS 타입 (EMAIL, NAVER)").attributes(key("required").value("Yes")),
                                fieldWithPath("password").description("비밀번호 (EMAIL 타입일 때 필수)").attributes(key("required").value("Conditional"))
                        ),
                        responseFields(
                                fieldWithPath("email").description("회원 이메일").attributes(key("required").value("Yes")),
                                fieldWithPath("name").description("회원 이름").attributes(key("required").value("Yes"))
                        )
                ));
    }

    @Test
    void documentMemberDeletion() throws Exception {
        // given
        Long memberId = 1L;
        willDoNothing().given(memberDeleteUseCase).delete(memberId);

        // when & then
        mockMvc.perform(delete("/api/members/{id}", memberId))
                .andExpect(status().isOk())
                .andDo(document("member-deletion",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("삭제할 회원 ID")
                        )
                ));
    }
}
