package com.ddd.tooda.domain.stock.controller;

import com.ddd.tooda.domain.stock.dto.StockDto.SearchStockResponse;
import com.ddd.tooda.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(StockController.STOCK)
@RequiredArgsConstructor
public class StockController {

    public static final String STOCK = "/stock";
    private final StockService stockService;

    @GetMapping("search")
    public ResponseEntity<List<SearchStockResponse>> search(@RequestParam String q) {
        List<SearchStockResponse> responses = stockService.search(q);
        return ResponseEntity.ok(responses);
    }
}
