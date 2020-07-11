package com.levi9.code9.booksservice.service.impl;

import com.levi9.code9.booksservice.dto.AuthorDto;
import com.levi9.code9.booksservice.mapper.AuthorMapper;
import com.levi9.code9.booksservice.model.AuthorEntity;
import com.levi9.code9.booksservice.repository.AuthorRepository;
import com.levi9.code9.booksservice.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        final AuthorEntity authorToSave = authorMapper.map(authorDto);
        final AuthorEntity savedAuthor = authorRepository.save(authorToSave);
        final AuthorDto savedAuthorDto = authorMapper.mapToDto(savedAuthor);
        return savedAuthorDto;
    }

    @Override
    public List<AuthorDto> saveAll(List<AuthorDto> authorDtos) {
        List<AuthorEntity> authorEntities = new ArrayList<>();
        for (AuthorDto authorDto : authorDtos) {
            AuthorEntity author = authorMapper.map(authorDto);
            author.setId(null);
            authorEntities.add(author);
        }
        List<AuthorDto> savedAuthorsDto = new ArrayList<>(authorEntities.size());

        for (AuthorEntity authorEntity : authorEntities) {
            AuthorEntity savedAuthor = authorRepository.save(authorEntity);
            savedAuthorsDto.add(authorMapper.mapToDto(savedAuthor));
        }
//        List<AuthorEntity> savedAuthors = authorRepository.saveAll(authorEntities);
//        for (AuthorEntity savedAuthor : savedAuthors) {
//
//        }
        return savedAuthorsDto;
    }

    @Override
    public List<AuthorDto> getAll() {
        List<AuthorEntity> authors = authorRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<AuthorDto> authorDtos = new ArrayList<>(authors.size());
        authors.forEach(authorEntity -> authorDtos.add(authorMapper.mapToDto(authorEntity)));
        return authorDtos;
    }
}
