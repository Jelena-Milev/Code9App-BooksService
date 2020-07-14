package com.levi9.code9.booksservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookSaveDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantityOnStock;
    private boolean onStock;
    private Long soldCopiesNumber;

    private Long authorId;
    private List<Long> genresIds;
}
