package com.stockmetrics.adapter.web.stock.dto;

import com.stockmetrics.domain.stock.Exchange;

public record StockRegistrationRequest(String ticker, String name, Exchange exchange) {
}
