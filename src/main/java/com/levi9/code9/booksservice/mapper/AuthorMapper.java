package com.levi9.code9.booksservice.mapper;

import com.levi9.code9.booksservice.dto.AuthorDto;
import com.levi9.code9.booksservice.model.AuthorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorEntity map(AuthorDto authorDto);
    AuthorDto mapToDto(AuthorEntity authorEntity);
}
