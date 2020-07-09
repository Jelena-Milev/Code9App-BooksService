package com.levi9.code9.booksservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BookSaveDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantityOnStock;
    private boolean onSale;
    private Long soldCopiesNumber;

    private Long authorId;
    private List<Long> genresIds;
}
