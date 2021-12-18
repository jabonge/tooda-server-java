package com.ddd.tooda.domain.stock.dto;

import com.ddd.tooda.domain.stock.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class StockDto {

    @AllArgsConstructor
    @Getter
    public static class SearchStockResponse {
        private Long id;
        private String name;

        public static SearchStockResponse from(Stock stock) {
            return new SearchStockResponse(stock.getId(), stock.getName());
        }
    }
}
