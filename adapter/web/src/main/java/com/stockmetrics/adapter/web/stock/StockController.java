package com.stockmetrics.adapter.web.stock;

import com.stockmetrics.adapter.web.stock.dto.StockRegistrationRequest;
import com.stockmetrics.adapter.web.stock.dto.StockResponse;
import com.stockmetrics.application.provided.stock.StockRegistrationUseCase;
import com.stockmetrics.application.stock.RegisterStockCommand;
import com.stockmetrics.domain.stock.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockRegistrationUseCase stockRegistrationUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse register(@RequestBody StockRegistrationRequest request) {
        RegisterStockCommand command = new RegisterStockCommand(
                request.ticker(),
                request.name(),
                request.exchange()
        );
        Stock stock = stockRegistrationUseCase.register(command);
        return StockResponse.from(stock);
    }
}
