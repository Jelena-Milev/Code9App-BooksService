package com.levi9.code9.booksservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Book")
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @EqualsAndHashCode.Include
    private String title;
    private BigDecimal price;
    private Long quantityOnStock;
    private boolean onStock;
    private Long soldCopiesNumber;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private AuthorEntity author;

    @OneToMany(
            mappedBy = "book",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<BookGenre> genres;

    public void addBookGenre(GenreEntity genre) {
        if(genres == null){
            genres = new ArrayList<>();
        }
        final BookGenre bookGenre = new BookGenre(this, genre);
        genres.add(bookGenre);
    }

    public boolean containsGenre(GenreEntity genre){
        return genres.stream().anyMatch(bookGenre -> bookGenre.getGenre().equals(genre));
    }

    public void removeGenres(List<BookGenre> genresToRemove) {
        this.genres.removeAll(genresToRemove);
    }
}
