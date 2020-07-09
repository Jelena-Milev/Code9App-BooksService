package com.levi9.code9.booksservice.mapper;

import com.levi9.code9.booksservice.dto.BookDto;
import com.levi9.code9.booksservice.dto.BookSaveDto;
import com.levi9.code9.booksservice.model.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookEntity map(BookSaveDto bookDto);
    BookDto mapToDto(BookEntity bookEntity);
}
