package com.stockmetrics.adapter.web.stock.dto;

import com.stockmetrics.domain.stock.Exchange;
import com.stockmetrics.domain.stock.Stock;

public record StockResponse(String ticker, String name, Exchange exchange) {

    public static StockResponse from(Stock stock) {
        return new StockResponse(stock.getTicker(), stock.getName(), stock.getExchange());
    }
}
