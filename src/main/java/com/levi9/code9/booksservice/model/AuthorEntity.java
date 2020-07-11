package com.levi9.code9.booksservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "Author")
@Table(name="author")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof AuthorEntity)) return false;
        AuthorEntity that = (AuthorEntity) o;
        return firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                dateOfBirth.equals(that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth);
    }
}
