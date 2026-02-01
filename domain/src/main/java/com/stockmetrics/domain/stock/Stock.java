package com.stockmetrics.domain.stock;

import com.stockmetrics.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends AbstractEntity {

    private static final Pattern TICKER_PATTERN = Pattern.compile("^[A-Z]{1,5}(\\.[A-Z])?$");

    @Column(nullable = false, unique = true)
    private String ticker;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Exchange exchange;

    private Stock(String ticker, String name, Exchange exchange) {
        validateTicker(ticker);
        this.ticker = ticker;
        this.name = name;
        this.exchange = exchange;
    }

    public static Stock create(CreateStockRequest request) {
        return new Stock(request.ticker(), request.name(), request.exchange());
    }

    private static void validateTicker(String ticker) {
        if (ticker == null || !TICKER_PATTERN.matcher(ticker).matches()) {
            throw new IllegalArgumentException("Invalid ticker format: ticker must match ^[A-Z]{1,5}(\\.[A-Z])?$");
        }
    }
}
