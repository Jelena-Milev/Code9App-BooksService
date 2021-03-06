package com.levi9.code9.booksservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.levi9.code9.booksservice.dto.BookDto;
import com.levi9.code9.booksservice.dto.BookSaveDto;
import com.levi9.code9.booksservice.dto.CartItemDto;
import com.levi9.code9.booksservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getAll() {
        List<BookDto> bookDtos = bookService.getAll();
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/bulk", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getByIds(@RequestBody final List<Long> ids) {
        List<BookDto> books = new ArrayList<>(ids.size());
        ids.forEach(id -> {
            final BookDto bookDto = bookService.getById(id);
            books.add(bookDto);
        });
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> getById(@PathVariable final Long id) {
        BookDto bookDto = bookService.getById(id);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @GetMapping(path = "/best-sellers", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getBestSellers(@RequestParam(required = false) final Integer number) {
        List<BookDto> bookDtos;
        Integer numberOfBooks = number;
        if (numberOfBooks == null) {
            numberOfBooks = new Integer(10);
//            bookDtos = bookService.getBestSellers(new Integer(10));
        }
        bookDtos = bookService.getBestSellers(numberOfBooks);
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/filter", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> filter(@RequestParam(required = false) final String title,
                                                @RequestParam(required = false) final String author,
                                                @RequestParam(required = false) final Long genreId) {
        if (title == null && author == null & genreId == null) {
            return getAll();
        }
        if (genreId != null) {
            return filterByGenre(genreId);
        }
        List<BookDto> bookDtos = bookService.filterBooks(title, author);
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    public ResponseEntity<List<BookDto>> filterByGenre(Long genreId){
        List<BookDto> bookDtos = bookService.filterByGenre(genreId);
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @PostMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> save(@RequestBody final @Valid BookSaveDto bookToSaveDto) {
        BookDto savedBook = bookService.save(bookToSaveDto);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BookDto> delete(@PathVariable final Long id) {
        BookDto deletedBook = bookService.delete(id);
        return new ResponseEntity<>(deletedBook, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> update(@RequestBody final @Valid BookSaveDto bookToSaveDto, @PathVariable final Long id) {
        BookDto updatedBook = bookService.update(id, bookToSaveDto);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PatchMapping(path = "/bulk", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> updateBooksSold(@RequestBody final List<CartItemDto> cartItemsSold) {
        List<BookDto> updatedBooks = bookService.updateCopiesSold(cartItemsSold);
        return new ResponseEntity<>(updatedBooks, HttpStatus.OK);
    }
}
