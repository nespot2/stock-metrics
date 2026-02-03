package com.stockmetrics.application.provided.stock;

import com.stockmetrics.application.stock.UpdateStockNameCommand;
import com.stockmetrics.domain.stock.Stock;

public interface StockModificationUseCase {
    Stock updateName(UpdateStockNameCommand command);
}
