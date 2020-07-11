package com.levi9.code9.booksservice.service;

import com.levi9.code9.booksservice.dto.AuthorDto;
import com.levi9.code9.booksservice.model.AuthorEntity;


import java.util.List;

public interface AuthorService {

    AuthorDto save(AuthorDto authorDto);
    List<AuthorDto> saveAll(List<AuthorDto> authorDtos);
    List<AuthorDto> getAll();
}
