package com.levi9.code9.booksservice.repository;

import com.levi9.code9.booksservice.model.BookGenre;
import com.levi9.code9.booksservice.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {

    List<BookGenre> findByGenreAndBookOnStockIsTrue(GenreEntity genre);
}
