package com.ddd.tooda.domain.stock.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "ix_stock_name", unique = true, columnList = "name"),
        @Index(name = "ix_stock_marketCap", columnList = "marketCap")
})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @JsonIgnore
    @Column()
    Long marketCap;
}
