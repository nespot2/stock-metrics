package com.stockmetrics.application.provided.stock;

import com.stockmetrics.application.stock.RegisterStockCommand;
import com.stockmetrics.domain.stock.Stock;

public interface StockRegistrationUseCase {
    Stock register(RegisterStockCommand command);
}
