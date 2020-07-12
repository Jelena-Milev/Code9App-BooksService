package com.levi9.code9.booksservice.repository;

import com.levi9.code9.booksservice.model.AuthorEntity;
import com.levi9.code9.booksservice.model.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByOnStockIsTrue();
    
    List<BookEntity> findAllByOnStockIsTrue(Pageable pageable);

    List<BookEntity> findByTitleStartingWithAndOnStockIsTrue(String title);

    List<BookEntity> findByAuthorAndOnStockIsTrue(AuthorEntity author);

    List<BookEntity> findByAuthorAndTitleStartingWithAndOnStockIsTrue(AuthorEntity author, String title);
}
