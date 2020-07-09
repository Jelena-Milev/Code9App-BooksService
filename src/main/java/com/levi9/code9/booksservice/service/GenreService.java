package com.levi9.code9.booksservice.service;

import com.levi9.code9.booksservice.dto.BookDto;
import com.levi9.code9.booksservice.dto.GenreDto;
import com.levi9.code9.booksservice.model.GenreEntity;

import java.util.List;

public interface GenreService {

    GenreDto save(GenreDto genreDto);

    List<GenreDto> getAll();

}
