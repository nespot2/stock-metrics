package com.stockmetrics.bootstrap.api.stock;

import com.stockmetrics.adapter.web.exception.GlobalExceptionHandler;
import com.stockmetrics.adapter.web.stock.StockController;
import com.stockmetrics.application.provided.stock.StockModificationUseCase;
import com.stockmetrics.application.provided.stock.StockRegistrationUseCase;
import com.stockmetrics.application.stock.RegisterStockCommand;
import com.stockmetrics.application.stock.UpdateStockNameCommand;
import com.stockmetrics.domain.stock.CreateStockRequest;
import com.stockmetrics.domain.stock.Exchange;
import com.stockmetrics.domain.stock.Stock;
import java.util.NoSuchElementException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

    @MockitoBean
    private StockModificationUseCase stockModificationUseCase;

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

    @Test
    void shouldReturn200OkOnSuccessfulStockNameUpdate() throws Exception {
        // given
        String ticker = "AAPL";
        String requestBody = """
                {
                    "name": "Apple Inc. Updated"
                }
                """;

        Stock updatedStock = Stock.create(new CreateStockRequest(ticker, "Apple Inc. Updated", Exchange.NASDAQ));
        given(stockModificationUseCase.updateName(any(UpdateStockNameCommand.class)))
                .willReturn(updatedStock);

        // when & then
        mockMvc.perform(patch("/api/stocks/{ticker}", ticker)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker").value(ticker))
                .andExpect(jsonPath("$.name").value("Apple Inc. Updated"));
    }

    @Test
    void shouldReturn404NotFoundIfStockDoesNotExist() throws Exception {
        // given
        String ticker = "UNKNOWN";
        String requestBody = """
                {
                    "name": "New Name"
                }
                """;

        willThrow(new NoSuchElementException("Stock with ticker " + ticker + " not found"))
                .given(stockModificationUseCase).updateName(any(UpdateStockNameCommand.class));

        // when & then
        mockMvc.perform(patch("/api/stocks/{ticker}", ticker)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Stock with ticker " + ticker + " not found"));
    }
}
