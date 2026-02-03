package com.stockmetrics.application.stock;

import com.stockmetrics.application.required.stock.StockRepository;
import com.stockmetrics.domain.stock.Exchange;
import com.stockmetrics.domain.stock.Stock;
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

    @Test
    @DisplayName("Should reject if stock already exists")
    void shouldRejectIfStockAlreadyExists() {
        // given
        String ticker = "AAPL";
        RegisterStockCommand command = new RegisterStockCommand(ticker, "Apple Inc.", Exchange.NASDAQ);
        Stock existingStock = Stock.create(new com.stockmetrics.domain.stock.CreateStockRequest(ticker, "Apple Inc.", Exchange.NASDAQ));
        given(stockRepository.findByTicker(ticker)).willReturn(Optional.of(existingStock));

        // when & then
        assertThatThrownBy(() -> stockService.register(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already exists");

        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    @DisplayName("Should register a stock successfully")
    void shouldRegisterStock() {
        // given
        String ticker = "AAPL";
        String name = "Apple Inc.";
        Exchange exchange = Exchange.NASDAQ;
        RegisterStockCommand command = new RegisterStockCommand(ticker, name, exchange);

        given(stockRepository.findByTicker(ticker)).willReturn(Optional.empty());
        given(stockRepository.save(any(Stock.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Stock result = stockService.register(command);

        // then
        assertThat(result.getTicker()).isEqualTo(ticker);
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getExchange()).isEqualTo(exchange);
        verify(stockRepository).save(any(Stock.class));
    }

    @Test
    @DisplayName("Should modify stock name using service")
    void shouldModifyStockName() {
        // given
        String ticker = "AAPL";
        String originalName = "Apple Inc.";
        String newName = "Apple Corporation";
        Exchange exchange = Exchange.NASDAQ;

        Stock existingStock = Stock.create(new com.stockmetrics.domain.stock.CreateStockRequest(ticker, originalName, exchange));
        given(stockRepository.findByTicker(ticker)).willReturn(Optional.of(existingStock));

        UpdateStockNameCommand command = new UpdateStockNameCommand(ticker, newName);

        // when
        Stock result = stockService.updateName(command);

        // then
        assertThat(result.getName()).isEqualTo(newName);
        assertThat(result.getTicker()).isEqualTo(ticker);
    }

    @Test
    @DisplayName("Should reject update if stock does not exist")
    void shouldRejectUpdateIfStockDoesNotExist() {
        // given
        String ticker = "AAPL";
        String newName = "Apple Corporation";

        given(stockRepository.findByTicker(ticker)).willReturn(Optional.empty());

        UpdateStockNameCommand command = new UpdateStockNameCommand(ticker, newName);

        // when & then
        assertThatThrownBy(() -> stockService.updateName(command))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("not found");
    }

    @Test
    @DisplayName("Should delete a stock by changing its status to DELETED")
    void shouldDeleteStock() {
        // given
        String ticker = "AAPL";
        Stock existingStock = Stock.create(new com.stockmetrics.domain.stock.CreateStockRequest(ticker, "Apple Inc.", Exchange.NASDAQ));
        given(stockRepository.findByTicker(ticker)).willReturn(Optional.of(existingStock));

        DeleteStockCommand command = new DeleteStockCommand(ticker);

        // when
        stockService.delete(command);

        // then
        assertThat(existingStock.getStatus()).isEqualTo(com.stockmetrics.domain.stock.StockStatus.DELETED);
    }

    @Test
    @DisplayName("Should reject delete if stock does not exist")
    void shouldRejectDeleteIfStockDoesNotExist() {
        // given
        String ticker = "AAPL";
        given(stockRepository.findByTicker(ticker)).willReturn(Optional.empty());

        DeleteStockCommand command = new DeleteStockCommand(ticker);

        // when & then
        assertThatThrownBy(() -> stockService.delete(command))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("not found");
    }
}
