package com.levi9.code9.booksservice.service.impl;

import com.levi9.code9.booksservice.dto.BookDto;
import com.levi9.code9.booksservice.dto.BookSaveDto;
import com.levi9.code9.booksservice.mapper.BookMapper;
import com.levi9.code9.booksservice.model.AuthorEntity;
import com.levi9.code9.booksservice.model.BookEntity;
import com.levi9.code9.booksservice.model.GenreEntity;
import com.levi9.code9.booksservice.repository.AuthorRepository;
import com.levi9.code9.booksservice.repository.BookRepository;
import com.levi9.code9.booksservice.repository.GenreRepository;
import com.levi9.code9.booksservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    private final GenreRepository genreRepository;
    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, GenreRepository genreRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public BookDto save(BookSaveDto bookDtoToSave) {
        BookEntity bookEntityToSave = bookMapper.map(bookDtoToSave);

//        BookEntity almostSavedBook = bookRepository.save(bookEntityToSave);
        BookEntity bookWithAuthor = this.addAuthor(bookEntityToSave, bookDtoToSave.getAuthorId());


        bookDtoToSave.getGenresIds().forEach(genreId -> {
            GenreEntity genre = genreRepository.findById(genreId).get();
            bookWithAuthor.addGenre(genre);
        });

        BookEntity savedBook = bookRepository.saveAndFlush(bookWithAuthor);
        return bookMapper.mapToDto(savedBook);
    }

    @Override
    public List<BookDto> getAll() {
        List<BookEntity> books = bookRepository.findAllByIsSellingIsTrue();
        List<BookDto> bookDtos = new ArrayList<>(books.size());
        books.forEach(book -> bookDtos.add(bookMapper.mapToDto(book)));
        return bookDtos;
    }

    @Override
    public List<BookDto> getBestSellers(Integer number) {
        if(number == null){
            number = new Integer(10);
        }
        Pageable sortedByPiecesSold = PageRequest.of(0, number, Sort.by("soldCopiesNumber").descending());
        List<BookEntity> bestSellers = bookRepository.findAllByIsSellingIsTrue(sortedByPiecesSold);
        List<BookDto> bestSellersDtos = new ArrayList<>(bestSellers.size());
        bestSellers.forEach(book -> bestSellersDtos.add(bookMapper.mapToDto(book)));
        return bestSellersDtos;
    }

    @Override
    public List<BookDto> filterBooks(String title, String author) {
        List<BookEntity> filteredBooks = new LinkedList<>();
        if (title != null && author == null) {
            filteredBooks = bookRepository.findByTitleStartingWithAndIsSellingIsTrue(title);
        }
        else {
            List<AuthorEntity> authors = authorRepository.findByFirstNameStartingWithOrLastNameStartingWith(author, author);
            List<BookEntity> books = new LinkedList<>();
            if (title == null) {
                authors.forEach(a -> {
                    books.addAll(bookRepository.findByAuthorAndIsSellingIsTrue(a));
                });
            } else {
                authors.forEach(a -> {
                    books.addAll(bookRepository.findByAuthorAndTitleStartingWithAndIsSellingIsTrue(a, title));
                });
            }
            filteredBooks.addAll(books);
        }
        List<BookDto> bookDtos = new ArrayList<>(filteredBooks.size());
        filteredBooks.forEach(book -> bookDtos.add(bookMapper.mapToDto(book)));
        return bookDtos;
    }

    @Override
    //TODO: if book is deleted, throws an error
    public BookDto delete(Long id) {
        BookEntity bookToDelete = bookRepository.findById(id).get();
        bookToDelete.setSelling(false);
        BookEntity deletedBook = bookRepository.save(bookToDelete);
        return bookMapper.mapToDto(deletedBook);
    }

    @Override
    public BookDto update(Long id, BookSaveDto bookToSaveDto) {
        return null;
    }

    @Override
    public BookDto updateCopiesSaled(Long id, Long copiesSold) {
        return null;
    }

    @Override
    public BookDto getById(Long id) {
        return null;
    }

    @Override
    public List<BookDto> filterByGenre(Long genreId) {
        GenreEntity genre = genreRepository.findById(genreId).get();
        List<BookEntity> books = bookRepository.findByGenresContainsAndIsSellingIsTrue(genre);
        List<BookDto> bookDtos = new ArrayList<>(books.size());
        books.forEach(book -> bookDtos.add(bookMapper.mapToDto(book)));
        return bookDtos;
    }

    private BookEntity addAuthor(BookEntity book, Long authorId) {
        AuthorEntity authorEntity = authorRepository.findById(authorId).get();
        book.setAuthor(authorEntity);
        return bookRepository.save(book);
    }
}
