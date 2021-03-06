package com.levi9.code9.booksservice.service.impl;

import com.levi9.code9.booksservice.dto.AuthorDto;
import com.levi9.code9.booksservice.exception.ObjectAlreadyExistsException;
import com.levi9.code9.booksservice.mapper.AuthorMapper;
import com.levi9.code9.booksservice.model.AuthorEntity;
import com.levi9.code9.booksservice.repository.AuthorRepository;
import com.levi9.code9.booksservice.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        final Optional<AuthorEntity> author = authorRepository.findByFirstNameAndLastName(authorDto.getFirstName().trim(), authorDto.getLastName().trim());
        if(author.isPresent()){
            throw new ObjectAlreadyExistsException("Author");
        }
        final AuthorEntity authorToSave = authorMapper.map(authorDto);
        final AuthorEntity savedAuthor = authorRepository.save(authorToSave);
        final AuthorDto savedAuthorDto = authorMapper.mapToDto(savedAuthor);
        return savedAuthorDto;
    }

    @Override
    public List<AuthorDto> getAll() {
        List<AuthorEntity> authors = authorRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<AuthorDto> authorDtos = new ArrayList<>(authors.size());
        authors.forEach(authorEntity -> authorDtos.add(authorMapper.mapToDto(authorEntity)));
        return authorDtos;
    }
}
