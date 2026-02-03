package com.stockmetrics.domain.stock;

public class StockFixture {

    public static Stock createStock() {
        return Stock.create(new CreateStockRequest("AAPL", "Apple Inc.", Exchange.NASDAQ));
    }

    public static Stock createStock(String ticker, String name, Exchange exchange) {
        return Stock.create(new CreateStockRequest(ticker, name, exchange));
    }
}
