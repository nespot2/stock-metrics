package com.stockmetrics.domain.stock;

public record CreateStockRequest(String ticker, String name, Exchange exchange) {
}
