package com.levi9.code9.booksservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

//    @OneToMany(
//            mappedBy = "author",
//            fetch = FetchType.LAZY
//    )
//    private Set<BookEntity> books;
}
