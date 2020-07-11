package com.levi9.code9.booksservice.repository;

import com.levi9.code9.booksservice.model.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
}
