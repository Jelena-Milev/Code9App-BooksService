package com.levi9.code9.booksservice.repository;

import com.levi9.code9.booksservice.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    List<AuthorEntity> findByFirstNameStartingWithOrLastNameStartingWith(String firstName, String lastName);

    Optional<AuthorEntity> findByFirstNameAndLastName(String firstName, String lastName);
}