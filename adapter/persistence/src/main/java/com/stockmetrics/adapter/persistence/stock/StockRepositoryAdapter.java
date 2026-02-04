package com.stockmetrics.adapter.persistence.stock;

import com.stockmetrics.application.required.stock.StockRepository;
import com.stockmetrics.domain.stock.Stock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StockRepositoryAdapter implements StockRepository {

    private final StockJpaRepository stockJpaRepository;

    public StockRepositoryAdapter(StockJpaRepository stockJpaRepository) {
        this.stockJpaRepository = stockJpaRepository;
    }

    @Override
    public Stock save(Stock stock) {
        return stockJpaRepository.save(stock);
    }

    @Override
    public Optional<Stock> findByTicker(String ticker) {
        return stockJpaRepository.findByTicker(ticker);
    }


}
