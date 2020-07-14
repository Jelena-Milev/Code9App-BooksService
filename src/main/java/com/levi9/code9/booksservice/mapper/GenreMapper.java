package com.levi9.code9.booksservice.mapper;

import com.levi9.code9.booksservice.dto.GenreDto;
import com.levi9.code9.booksservice.model.GenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreEntity map(GenreDto genreDto);
    GenreDto mapToDto(GenreEntity genreEntity);
}
