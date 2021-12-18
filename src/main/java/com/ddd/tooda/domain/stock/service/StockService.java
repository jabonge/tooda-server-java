package com.ddd.tooda.domain.stock.service;

import com.ddd.tooda.domain.stock.dto.StockDto.SearchStockResponse;
import com.ddd.tooda.domain.stock.model.Stock;
import com.ddd.tooda.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public List<SearchStockResponse> search(String q) {
        PageRequest request =  PageRequest.of(0,20, Sort.by("marketCap").descending());
        List<Stock> stocks = stockRepository.findByNameContains(q, request);
        return stocks.stream().map(
                SearchStockResponse::from
        ).collect(Collectors.toList());
    }
}
