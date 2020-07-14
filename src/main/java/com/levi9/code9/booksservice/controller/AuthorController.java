package com.levi9.code9.booksservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import com.levi9.code9.booksservice.dto.AuthorDto;
import com.levi9.code9.booksservice.model.AuthorEntity;
import com.levi9.code9.booksservice.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path = "", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDto>> getAll(){
        List<AuthorDto> authorDtos = authorService.getAll();
        return new ResponseEntity<>(authorDtos, HttpStatus.OK);
    }

    @PostMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> save(@RequestBody final AuthorDto authorDto){
        AuthorDto savedAuthor = authorService.save(authorDto);
        return new ResponseEntity<>(savedAuthor, HttpStatus.OK);
    }

    @PostMapping(path = "/all", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDto>> saveAll(@RequestBody final List<AuthorDto> authorDtos){
//        List<AuthorDto> savedAuthors = authorService.saveAll(authorDtos);
//        return new ResponseEntity<>(savedAuthors, HttpStatus.OK);
        List<AuthorDto> savedAuthors = new ArrayList<>(authorDtos.size());
        for (AuthorDto authorDto : authorDtos) {
            final AuthorDto savedAuthor = authorService.save(authorDto);
            savedAuthors.add(savedAuthor);
        }
        return new ResponseEntity<>(savedAuthors, HttpStatus.OK);
    }
}
