package com.stockmetrics.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @Test
    @DisplayName("Should create a Stock with ticker, name and exchange using request class")
    void shouldCreateStockWithTickerNameAndExchange() {
        // given
        CreateStockRequest request = new CreateStockRequest("AAPL", "Apple Inc.", Exchange.NASDAQ);

        // when
        Stock stock = Stock.create(request);

        // then
        assertThat(stock.getTicker()).isEqualTo("AAPL");
        assertThat(stock.getName()).isEqualTo("Apple Inc.");
        assertThat(stock.getExchange()).isEqualTo(Exchange.NASDAQ);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aapl", "TOOLONG", "123", "AA BB", "A@PL", ""})
    @DisplayName("Should reject ticker not following format ^[A-Z]{1,5}(\\.[A-Z])?$")
    void shouldRejectInvalidTicker(String invalidTicker) {
        // given
        CreateStockRequest request = new CreateStockRequest(invalidTicker, "Apple Inc.", Exchange.NASDAQ);

        // when & then
        assertThatThrownBy(() -> Stock.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ticker");
    }

    @Test
    @DisplayName("Should modify a name in Stock entity")
    void shouldModifyName() {
        // given
        Stock stock = StockFixture.createStock();
        String newName = "Apple Inc. Updated";

        // when
        stock.modifyName(newName);

        // then
        assertThat(stock.getName()).isEqualTo(newName);
    }

}
