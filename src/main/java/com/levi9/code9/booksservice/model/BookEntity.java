package com.levi9.code9.booksservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "Book")
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantityOnStock;
    private boolean isSelling;
    private Long soldCopiesNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinTable(name = "Genre_Books", joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "id")})
    private Set<GenreEntity> genres;

    public void addGenre(GenreEntity genre) {
        if (genres == null) {
            genres = new HashSet<>();
        }
        genres.add(genre);
        genre.getBooks().add(this);
    }

}
