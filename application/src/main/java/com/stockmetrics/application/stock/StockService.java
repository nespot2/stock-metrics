package com.stockmetrics.application.stock;

import com.stockmetrics.domain.stock.CreateStockRequest;
import com.stockmetrics.domain.stock.Stock;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock register(RegisterStockCommand command) {
        CreateStockRequest request = new CreateStockRequest(
                command.ticker(),
                command.name(),
                command.exchange()
        );
        Stock stock = Stock.create(request);
        return stockRepository.save(stock);
    }
}
