package com.levi9.code9.booksservice.repository;

import com.levi9.code9.booksservice.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    Optional<GenreEntity> findByName(String name);
}
