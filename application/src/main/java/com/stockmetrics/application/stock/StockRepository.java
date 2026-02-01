package com.stockmetrics.application.stock;

import com.stockmetrics.domain.stock.Stock;

import java.util.Optional;

public interface StockRepository {
    Stock save(Stock stock);
    Optional<Stock> findByTicker(String ticker);
}
