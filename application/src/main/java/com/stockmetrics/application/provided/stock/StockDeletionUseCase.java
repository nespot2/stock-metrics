package com.stockmetrics.application.provided.stock;

import com.stockmetrics.application.stock.DeleteStockCommand;

public interface StockDeletionUseCase {
    void delete(DeleteStockCommand command);
}
