package com.levi9.code9.booksservice.service.impl;

import com.levi9.code9.booksservice.dto.BookCopiesSoldDto;
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

import java.awt.print.Book;
import java.util.*;

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

        BookEntity bookWithAuthor = this.addAuthor(bookEntityToSave, bookDtoToSave.getAuthorId());
        BookEntity bookWithAuthorAndId = bookRepository.save(bookWithAuthor);

        bookDtoToSave.getGenresIds().forEach(genreId -> {
            GenreEntity genre = genreRepository.findById(genreId).get();
            bookWithAuthorAndId.addGenre(genre);
        });
        BookEntity savedBook = bookRepository.saveAndFlush(bookWithAuthorAndId);
        return bookMapper.mapToDto(savedBook);
    }

    @Override
    public List<BookDto> getAll() {
        List<BookEntity> books = bookRepository.findAllByOnStockIsTrue();
        List<BookDto> bookDtos = new ArrayList<>(books.size());
        books.forEach(book -> bookDtos.add(bookMapper.mapToDto(book)));
        return bookDtos;
    }

    @Override
    public List<BookDto> getBestSellers(Integer number) {
        Pageable sortedByPiecesSold = PageRequest.of(0, number, Sort.by("soldCopiesNumber").descending());
        List<BookEntity> bestSellers = bookRepository.findAllByOnStockIsTrue(sortedByPiecesSold);
        List<BookDto> bestSellersDtos = new ArrayList<>(bestSellers.size());
        bestSellers.forEach(book -> bestSellersDtos.add(bookMapper.mapToDto(book)));
        return bestSellersDtos;
    }

    @Override
    public List<BookDto> filterBooks(String title, String author) {
        List<BookEntity> filteredBooks = new LinkedList<>();
        if (title != null && author == null) {
            filteredBooks = bookRepository.findByTitleStartingWithAndOnStockIsTrue(title);
        } else {
            List<AuthorEntity> authors = authorRepository.findByFirstNameStartingWithOrLastNameStartingWith(author, author);
            List<BookEntity> books = new LinkedList<>();
            if (title == null) {
                authors.forEach(a -> {
                    books.addAll(bookRepository.findByAuthorAndOnStockIsTrue(a));
                });
            } else {
                authors.forEach(a -> {
                    books.addAll(bookRepository.findByAuthorAndTitleStartingWithAndOnStockIsTrue(a, title));
                });
            }
            filteredBooks.addAll(books);
        }
        List<BookDto> bookDtos = new ArrayList<>(filteredBooks.size());
        filteredBooks.forEach(book -> bookDtos.add(bookMapper.mapToDto(book)));
        return bookDtos;
    }

    @Override
    //TODO: if book is already deleted, throw an error
    public BookDto delete(Long id) {
        BookEntity bookToDelete = bookRepository.findById(id).get();
        bookToDelete.setOnStock(false);
        BookEntity deletedBook = bookRepository.save(bookToDelete);
        return bookMapper.mapToDto(deletedBook);
    }

    @Override
    public BookDto update(Long id, BookSaveDto newBook) {
        BookEntity bookToUpdate = bookRepository.findById(id).get();

        AuthorEntity newAuthor = authorRepository.findById(newBook.getAuthorId()).get();
        bookToUpdate.setAuthor(newAuthor);

        bookToUpdate.setTitle(newBook.getTitle());
        bookToUpdate.setPrice(newBook.getPrice());
        bookToUpdate.setQuantityOnStock(newBook.getQuantityOnStock());

//        updateGenres(bookToUpdate, newBook);

        BookEntity updatedBook = bookRepository.save(bookToUpdate);
        return bookMapper.mapToDto(updatedBook);
    }

    private void updateGenres(BookEntity bookToUpdate, BookSaveDto newBook) {
        Iterator<GenreEntity> iterator = bookToUpdate.getGenres().iterator();
        while(iterator.hasNext()){
            GenreEntity genreEntity = iterator.next();
            if (newBook.getGenresIds().contains(genreEntity.getId()) == false)
                bookToUpdate.removeGenre(genreEntity);
        }
        newBook.getGenresIds().forEach(genreId->{
            GenreEntity newGenre = genreRepository.findById(genreId).get();
            if(bookToUpdate.getGenres().contains(newGenre) == false){
                bookToUpdate.addGenre(newGenre);
            }
        });
    }

    @Override
    public BookDto updateCopiesSold(Long id, BookCopiesSoldDto copiesSold) {
        final BookEntity book = bookRepository.findById(id).get();
        Long updatedCopiesSoldNumber = book.getSoldCopiesNumber()+copiesSold.getCopiesSold();
        book.setSoldCopiesNumber(updatedCopiesSoldNumber);
        final BookEntity savedBook = bookRepository.save(book);
        return bookMapper.mapToDto(savedBook);
    }

    @Override
    public BookDto getById(Long id) {
        final BookEntity book = bookRepository.findById(id).get();
        return bookMapper.mapToDto(book);
    }

    @Override
    public List<BookDto> filterByGenre(Long genreId) {
        GenreEntity genre = genreRepository.findById(genreId).get();
        List<BookEntity> books = bookRepository.findByGenresContainsAndOnStockIsTrue(genre);
        List<BookDto> bookDtos = new ArrayList<>(books.size());
        books.forEach(book -> bookDtos.add(bookMapper.mapToDto(book)));
        return bookDtos;
    }

    private BookEntity addAuthor(BookEntity book, Long authorId) {
        AuthorEntity authorEntity = authorRepository.findById(authorId).get();
        book.setAuthor(authorEntity);
        return book;
    }
}
