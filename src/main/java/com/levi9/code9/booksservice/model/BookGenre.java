package com.levi9.code9.booksservice.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "BookGenre")
@Table(name = "book_genre")
public class BookGenre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private BookEntity book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private GenreEntity genre;

    public BookGenre(BookEntity book, GenreEntity genre) {
        this.book = book;
        this.genre = genre;
    }
}
