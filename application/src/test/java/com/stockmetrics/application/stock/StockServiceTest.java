package com.stockmetrics.application.stock;

import com.stockmetrics.domain.stock.Exchange;
import com.stockmetrics.domain.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    private StockService stockService;

    @BeforeEach
    void setUp() {
        stockService = new StockService(stockRepository);
    }

    @Test
    @DisplayName("Should reject invalid ticker format")
    void shouldRejectInvalidTickerFormat() {
        // given
        RegisterStockCommand command = new RegisterStockCommand("invalid", "Apple Inc.", Exchange.NASDAQ);

        // when & then
        assertThatThrownBy(() -> stockService.register(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ticker");

        verify(stockRepository, never()).save(any(Stock.class));
    }
}
