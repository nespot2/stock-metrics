package com.stockmetrics.bootstrap.api.stock.docs;

import com.stockmetrics.adapter.web.exception.GlobalExceptionHandler;
import com.stockmetrics.adapter.web.stock.StockController;
import com.stockmetrics.application.provided.stock.StockDeletionUseCase;
import com.stockmetrics.application.provided.stock.StockModificationUseCase;
import com.stockmetrics.application.provided.stock.StockRegistrationUseCase;
import com.stockmetrics.application.stock.RegisterStockCommand;
import com.stockmetrics.application.stock.UpdateStockNameCommand;
import com.stockmetrics.domain.stock.CreateStockRequest;
import com.stockmetrics.domain.stock.Exchange;
import com.stockmetrics.domain.stock.Stock;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({StockController.class, GlobalExceptionHandler.class})
@AutoConfigureRestDocs
class StockApiDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StockRegistrationUseCase stockRegistrationUseCase;

    @MockitoBean
    private StockModificationUseCase stockModificationUseCase;

    @MockitoBean
    private StockDeletionUseCase stockDeletionUseCase;

    @Test
    void documentStockRegistration() throws Exception {
        // given
        String requestBody = """
                {
                    "ticker": "AAPL",
                    "name": "Apple Inc.",
                    "exchange": "NASDAQ"
                }
                """;

        given(stockRegistrationUseCase.register(any(RegisterStockCommand.class)))
                .willReturn(Stock.create(new CreateStockRequest("AAPL", "Apple Inc.", Exchange.NASDAQ)));

        // when & then
        mockMvc.perform(post("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(document("stock-registration",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("ticker").description("주식 티커 심볼 (1~5자 대문자, 선택적으로 .과 대문자 1자)").attributes(key("required").value("Yes")),
                                fieldWithPath("name").description("회사명").attributes(key("required").value("Yes")),
                                fieldWithPath("exchange").description("거래소 (NASDAQ, NYSE, NYSE_AMERICAN)").attributes(key("required").value("Yes"))
                        ),
                        responseFields(
                                fieldWithPath("ticker").description("주식 티커 심볼").attributes(key("required").value("Yes")),
                                fieldWithPath("name").description("회사명").attributes(key("required").value("Yes")),
                                fieldWithPath("exchange").description("거래소").attributes(key("required").value("Yes"))
                        )
                ));
    }

    @Test
    void documentStockModification() throws Exception {
        // given
        String ticker = "AAPL";
        String requestBody = """
                {
                    "name": "Apple Inc. Updated"
                }
                """;

        given(stockModificationUseCase.updateName(any(UpdateStockNameCommand.class)))
                .willReturn(Stock.create(new CreateStockRequest(ticker, "Apple Inc. Updated", Exchange.NASDAQ)));

        // when & then
        mockMvc.perform(patch("/api/stocks/{ticker}", ticker)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document("stock-modification",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("ticker").description("수정할 주식의 티커 심볼")
                        ),
                        requestFields(
                                fieldWithPath("name").description("수정할 회사명").attributes(key("required").value("Yes"))
                        ),
                        responseFields(
                                fieldWithPath("ticker").description("주식 티커 심볼").attributes(key("required").value("Yes")),
                                fieldWithPath("name").description("수정된 회사명").attributes(key("required").value("Yes")),
                                fieldWithPath("exchange").description("거래소").attributes(key("required").value("Yes"))
                        )
                ));
    }

    @Test
    void documentStockDeletion() throws Exception {
        // given
        String ticker = "AAPL";

        // when & then
        mockMvc.perform(delete("/api/stocks/{ticker}", ticker))
                .andExpect(status().isOk())
                .andDo(document("stock-deletion",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("ticker").description("삭제할 주식의 티커 심볼")
                        )
                ));
    }
}
