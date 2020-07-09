package com.levi9.code9.booksservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "Genre")
@Table(name = "genre")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH
    })
//    @JoinTable(name="Genre_Books", joinColumns = {@JoinColumn(name="genre_id")},
//    inverseJoinColumns = {@JoinColumn(name="book_id")})
    private Set<BookEntity> books;
}
