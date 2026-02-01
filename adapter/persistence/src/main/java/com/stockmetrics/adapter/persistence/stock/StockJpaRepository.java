package com.stockmetrics.adapter.persistence.stock;

import com.stockmetrics.domain.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockJpaRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByTicker(String ticker);
}
