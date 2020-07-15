package com.levi9.code9.booksservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GenreDto {
    private Long id;

    @NotBlank(message = "Genre name must not be blank")
    private String name;
}
