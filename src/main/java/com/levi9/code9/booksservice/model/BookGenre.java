package com.levi9.code9.booksservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "BookGenre")
@Table(name = "book_genre")
public class BookGenre {

    @EmbeddedId
    private BookGenreId id;

    //    @ManyToOne(fetch = FetchType.LAZY, cascade = {
//            CascadeType.PERSIST, CascadeType.MERGE
//    })
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    private BookEntity book;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = {
//            CascadeType.PERSIST, CascadeType.MERGE
//    })

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("genreId")
    private GenreEntity genre;

    public BookGenre(BookEntity book, GenreEntity genre) {
        this.book = book;
        this.genre = genre;
        this.id = new BookGenreId(book.getId(), genre.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BookGenre)) return false;
        BookGenre bookGenre = (BookGenre) o;
        return book.equals(bookGenre.book) &&
                genre.equals(bookGenre.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, genre);
    }

    @Override
    public String toString() {
        return "BookGenre{" +
                "id=" + id +
                ", book=" + book +
                ", genre=" + genre +
                '}';
    }
}
