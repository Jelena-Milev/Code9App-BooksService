package com.levi9.code9.booksservice.repository;

import com.levi9.code9.booksservice.model.AuthorEntity;
import com.levi9.code9.booksservice.model.BookEntity;
import com.levi9.code9.booksservice.model.GenreEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByOnStockIsTrue();
    
    List<BookEntity> findAllByOnStockIsTrue(Pageable pageable);

    List<BookEntity> findByTitleStartingWithAndOnStockIsTrue(String title);

    List<BookEntity> findByAuthorAndOnStockIsTrue(AuthorEntity author);

    List<BookEntity> findByAuthorAndTitleStartingWithAndOnStockIsTrue(AuthorEntity author, String title);

    List<BookEntity> findByGenresContainsAndOnStockIsTrue(GenreEntity genre);

    @Modifying
    @Query(value = "insert into book_genre(book_id, genre_id) values (:bookId, :genreId)",
    nativeQuery = true)
    void insertBookGenre(@Param("bookId") Long bookId, @Param("genreId") Long genreId);
}
