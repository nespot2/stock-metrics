package com.stockmetrics.application.stock;

public record UpdateStockNameCommand(String ticker, String newName) {
}
