package com.levi9.code9.booksservice.service.impl;

import com.levi9.code9.booksservice.dto.GenreDto;
import com.levi9.code9.booksservice.mapper.GenreMapper;
import com.levi9.code9.booksservice.model.GenreEntity;
import com.levi9.code9.booksservice.repository.GenreRepository;
import com.levi9.code9.booksservice.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Override
    public GenreDto save(GenreDto genreDto) {
        final GenreEntity savedGenre = genreRepository.save(genreMapper.map(genreDto));
        return genreMapper.mapToDto(savedGenre);
    }

    @Override
    public List<GenreDto> getAll() {
        List<GenreEntity> genres = genreRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        List<GenreDto> genreDtos = new ArrayList<>(genres.size());
        genres.forEach(genre -> genreDtos.add(genreMapper.mapToDto(genre)));
        return genreDtos;
    }
}
