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
@Entity(name = "Book")
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantityOnStock;
    private boolean onStock;
    private Long soldCopiesNumber;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BookGenre> genres;

    public void addGenre(GenreEntity genre) {
        if(genres == null){
            genres = new ArrayList<>();
        }
        BookGenre bookGenre = new BookGenre(this, genre);
        genres.add(bookGenre);
        genre.getBooks().add(bookGenre);
    }

    public void removeGenre(GenreEntity genre) {
        for (Iterator<BookGenre> iterator = genres.iterator();
             iterator.hasNext(); ) {
            BookGenre bookGenre = iterator.next();

            if (bookGenre.getBook().equals(this) &&
                    bookGenre.getGenre().equals(genre)) {
                iterator.remove();
                bookGenre.getGenre().getBooks().remove(bookGenre);
                bookGenre.setBook(null);
                bookGenre.setGenre(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BookEntity)) return false;
        BookEntity that = (BookEntity) o;
        return onStock == that.onStock &&
                title.equals(that.title) &&
                price.equals(that.price) &&
                quantityOnStock.equals(that.quantityOnStock) &&
                soldCopiesNumber.equals(that.soldCopiesNumber) &&
                author.equals(that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, quantityOnStock, onStock, soldCopiesNumber, author);
    }
}
