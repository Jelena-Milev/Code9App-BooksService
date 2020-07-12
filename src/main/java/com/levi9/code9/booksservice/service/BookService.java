package com.levi9.code9.booksservice.service;


import com.levi9.code9.booksservice.dto.BookCopiesSoldDto;
import com.levi9.code9.booksservice.dto.BookDto;
import com.levi9.code9.booksservice.dto.BookSaveDto;

import java.util.List;

public interface BookService {

    BookDto save(BookSaveDto bookToSave);

    List<BookDto> getAll();

    List<BookDto> getBestSellers(Integer number);

    List<BookDto> filterBooks(String title, String author);

    BookDto delete(Long id);

    BookDto update(Long id, BookSaveDto bookToSaveDto);

    BookDto getById(Long id);

    List<BookDto> filterByGenre(Long genreId);

    List<BookDto> updateCopiesSold(List<BookCopiesSoldDto> copiesSold);
}
