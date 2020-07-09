package com.levi9.code9.booksservice.repository;

import com.levi9.code9.booksservice.model.AuthorEntity;
import com.levi9.code9.booksservice.model.BookEntity;
import com.levi9.code9.booksservice.model.GenreEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByIsSellingIsTrue();
    
    List<BookEntity> findAllByIsSellingIsTrue(Pageable pageable);

    List<BookEntity> findByTitleStartingWithAndIsSellingIsTrue(String title);

    List<BookEntity> findByAuthorAndIsSellingIsTrue(AuthorEntity author);

    List<BookEntity> findByAuthorAndTitleStartingWithAndIsSellingIsTrue(AuthorEntity author, String title);

    List<BookEntity> findByGenresContainsAndIsSellingIsTrue(GenreEntity genre);
}
