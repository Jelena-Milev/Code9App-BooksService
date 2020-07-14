package com.levi9.code9.booksservice.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
