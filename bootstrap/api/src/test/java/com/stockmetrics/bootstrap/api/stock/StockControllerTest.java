package com.stockmetrics.bootstrap.api.stock;

import com.stockmetrics.adapter.web.exception.GlobalExceptionHandler;
import com.stockmetrics.adapter.web.stock.StockController;
import com.stockmetrics.application.provided.stock.StockRegistrationUseCase;
import com.stockmetrics.application.stock.RegisterStockCommand;
import com.stockmetrics.domain.stock.CreateStockRequest;
import com.stockmetrics.domain.stock.Exchange;
import com.stockmetrics.domain.stock.Stock;
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
@Import({StockController.class, GlobalExceptionHandler.class})
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StockRegistrationUseCase stockRegistrationUseCase;

    @Test
    void shouldReturn201CreatedOnSuccessfulStockRegistration() throws Exception {
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
                .andExpect(jsonPath("$.ticker").value("AAPL"))
                .andExpect(jsonPath("$.name").value("Apple Inc."))
                .andExpect(jsonPath("$.exchange").value("NASDAQ"));
    }

    @Test
    void shouldReturn400BadRequestOnInvalidTickerFormat() throws Exception {
        // given
        String requestBody = """
                {
                    "ticker": "invalid-ticker",
                    "name": "Test Company",
                    "exchange": "NASDAQ"
                }
                """;

        willThrow(new IllegalArgumentException("Invalid ticker format: ticker must match ^[A-Z]{1,5}(\\.[A-Z])?$"))
                .given(stockRegistrationUseCase).register(any(RegisterStockCommand.class));

        // when & then
        mockMvc.perform(post("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid ticker format: ticker must match ^[A-Z]{1,5}(\\.[A-Z])?$"));
    }
}
