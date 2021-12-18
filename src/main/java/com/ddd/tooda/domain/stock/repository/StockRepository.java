package com.ddd.tooda.domain.stock.repository;

import com.ddd.tooda.domain.stock.model.Stock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {

    List<Stock> findByNameContains(String name, Pageable pageable);
}
