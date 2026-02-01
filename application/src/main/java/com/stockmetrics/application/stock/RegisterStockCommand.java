package com.stockmetrics.application.stock;

import com.stockmetrics.domain.stock.Exchange;

public record RegisterStockCommand(String ticker, String name, Exchange exchange) {
}
