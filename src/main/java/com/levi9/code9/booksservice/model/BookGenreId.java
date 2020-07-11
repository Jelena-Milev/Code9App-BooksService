package com.levi9.code9.booksservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class BookGenreId implements Serializable {

    @Column(name="book_id")
    private Long bookId;

    @Column(name="genre_id")
    private Long genreId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BookGenreId)) return false;
        BookGenreId that = (BookGenreId) o;
        return bookId.equals(that.bookId) &&
                genreId.equals(that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, genreId);
    }

    @Override
    public String toString() {
        return "BookGenreId{" +
                "bookId=" + bookId +
                ", genreId=" + genreId +
                '}';
    }
}
