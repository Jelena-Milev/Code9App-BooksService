package com.levi9.code9.booksservice.mapper;

import com.levi9.code9.booksservice.dto.BookDto;
import com.levi9.code9.booksservice.dto.BookSaveDto;
import com.levi9.code9.booksservice.dto.GenreDto;
import com.levi9.code9.booksservice.model.BookEntity;
import com.levi9.code9.booksservice.model.BookGenre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookEntity map(BookSaveDto bookDto);
    BookDto mapToDto(BookEntity bookEntity);

    @Mapping(source = "bookGenre.genre.id", target = "id")
    @Mapping(source = "bookGenre.genre.name", target = "name")
    GenreDto bookGenreToGenreDto(BookGenre bookGenre);

}
