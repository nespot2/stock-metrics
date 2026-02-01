package com.stockmetrics.application.stock;

import com.stockmetrics.application.provided.stock.StockRegistrationUseCase;
import com.stockmetrics.application.required.stock.StockRepository;
import com.stockmetrics.domain.stock.CreateStockRequest;
import com.stockmetrics.domain.stock.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService implements StockRegistrationUseCase {

    private final StockRepository stockRepository;

    @Override
    public Stock register(RegisterStockCommand command) {
        CreateStockRequest request = new CreateStockRequest(
                command.ticker(),
                command.name(),
                command.exchange()
        );
        Stock stock = Stock.create(request);
        
        if (stockRepository.findByTicker(command.ticker()).isPresent()) {
            throw new IllegalStateException("Stock with ticker " + command.ticker() + " already exists");
        }
        
        return stockRepository.save(stock);
    }
}
