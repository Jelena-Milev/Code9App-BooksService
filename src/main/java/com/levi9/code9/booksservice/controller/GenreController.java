package com.levi9.code9.booksservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.levi9.code9.booksservice.dto.BookDto;
import com.levi9.code9.booksservice.dto.GenreDto;
import com.levi9.code9.booksservice.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(path = "", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreDto>> getAll(){
        List<GenreDto> genres = genreService.getAll();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @PostMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreDto> save(@RequestBody final GenreDto genreDto){
        final GenreDto savedGenre = genreService.save(genreDto);
        return new ResponseEntity<>(savedGenre, HttpStatus.OK);
    }
}
