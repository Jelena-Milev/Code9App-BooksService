package com.levi9.code9.booksservice.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookSaveDto {
    private Long id;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NonNull
    @Positive(message = "Price must be positive number or zero")
    private BigDecimal price;

    @NonNull
    @Positive(message = "Number of pieces on stock must be positive number or zero")
    private Long quantityOnStock;

    @AssertTrue(message = "Book must be on stock (onStock = true)")
    private boolean onStock;

    @NonNull
    @PositiveOrZero(message = "Sold copies number must be positive number or zero")
    private Long soldCopiesNumber;

    @NotNull(message = "Book must have an author")
    private Long authorId;

    @NotEmpty(message = "Book must have at least one genre")
    private List<Long> genresIds;
}
