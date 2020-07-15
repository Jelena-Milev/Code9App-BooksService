package com.levi9.code9.booksservice.service.impl;

import com.levi9.code9.booksservice.dto.BookDto;
import com.levi9.code9.booksservice.dto.BookSaveDto;
import com.levi9.code9.booksservice.dto.CartItemDto;
import com.levi9.code9.booksservice.exception.ObjectAlreadyExistsException;
import com.levi9.code9.booksservice.exception.ObjectNotFoundException;
import com.levi9.code9.booksservice.mapper.BookMapper;
import com.levi9.code9.booksservice.model.AuthorEntity;
import com.levi9.code9.booksservice.model.BookEntity;
import com.levi9.code9.booksservice.model.BookGenre;
import com.levi9.code9.booksservice.model.GenreEntity;
import com.levi9.code9.booksservice.repository.AuthorRepository;
import com.levi9.code9.booksservice.repository.BookGenreRepository;
import com.levi9.code9.booksservice.repository.BookRepository;
import com.levi9.code9.booksservice.repository.GenreRepository;
import com.levi9.code9.booksservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookGenreRepository bookGenreRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, GenreRepository genreRepository, AuthorRepository authorRepository, BookGenreRepository bookGenreRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.bookGenreRepository = bookGenreRepository;
    }


    @Override
    @Transactional
    public BookDto save(BookSaveDto bookDtoToSave) {
        final AuthorEntity author = findAuthorById(bookDtoToSave.getAuthorId());

        final Optional<BookEntity> optionalBook = bookRepository.findByTitleAndAuthor(bookDtoToSave.getTitle(), author);
        if(optionalBook.isPresent()){
            throw new ObjectAlreadyExistsException("Book");
        }

        final BookEntity bookToSave = bookMapper.map(bookDtoToSave);
        bookToSave.setAuthor(author);

        for (Long genreId : bookDtoToSave.getGenresIds()) {
            final GenreEntity genre = findGenreById(genreId);
            bookToSave.addGenre(genre);
        }
        final BookEntity savedBook = bookRepository.save(bookToSave);
        return bookMapper.mapToDto(savedBook);
    }

    @Override
    public List<BookDto> getAll() {
        List<BookEntity> books = bookRepository.findAllByOnStockIsTrue();
        return bookMapper.mapToDtoList(books);
    }

    @Override
    public List<BookDto> getBestSellers(Integer number) {
        Pageable sortedByPiecesSold = PageRequest.of(0, number.intValue(), Sort.by("soldCopiesNumber").descending());
        List<BookEntity> bestSellers = bookRepository.findAllByOnStockIsTrue(sortedByPiecesSold);
        return bookMapper.mapToDtoList(bestSellers);
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
        return bookMapper.mapToDtoList(filteredBooks);
    }

    @Override
    public BookDto delete(Long id) {
        final BookEntity bookToDelete = findBookById(id);
        bookToDelete.setOnStock(false);
        final BookEntity deletedBook = bookRepository.save(bookToDelete);
        return bookMapper.mapToDto(deletedBook);
    }

    @Override
    public BookDto update(Long bookToUpdateId, BookSaveDto newBook) {
        final BookEntity bookToUpdate = findBookById(bookToUpdateId);
        final AuthorEntity author = findAuthorById(newBook.getAuthorId());
        bookToUpdate.setAuthor(author);

        bookToUpdate.setTitle(newBook.getTitle());
        bookToUpdate.setPrice(newBook.getPrice());
        bookToUpdate.setQuantityOnStock(newBook.getQuantityOnStock());

        List<Long> newGenresIds = newBook.getGenresIds();
        updateGenres(bookToUpdate, newGenresIds);

        BookEntity updatedBook = bookRepository.save(bookToUpdate);
        return bookMapper.mapToDto(updatedBook);
    }

    private void updateGenres(BookEntity bookToUpdate, List<Long> newGenresIds){
        List<BookGenre> genresToRemove = new ArrayList<>();
        for (BookGenre bookGenre : bookToUpdate.getGenres()) {
            final Long genreId = bookGenre.getGenre().getId();
            if(!newGenresIds.contains(genreId)){
                genresToRemove.add(bookGenre);
            }
        }
        bookToUpdate.removeGenres(genresToRemove);
        for (Long newGenreId : newGenresIds) {
            final GenreEntity newGenre = findGenreById(newGenreId);
            if(!bookToUpdate.containsGenre(newGenre)){
                bookToUpdate.addGenre(newGenre);
            }
        }
    }

    @Override
    public BookDto getById(Long id) {
        final BookEntity book = findBookById(id);
        return bookMapper.mapToDto(book);
    }

    @Override
    public List<BookDto> filterByGenre(Long genreId) {
        final GenreEntity genre = findGenreById(genreId);
        final List<BookGenre> bookGenres = bookGenreRepository.findByGenreAndBookOnStockIsTrue(genre);
        final List<BookEntity> books = new ArrayList<>(bookGenres.size());
        bookGenres.forEach(bookGenre -> books.add(bookGenre.getBook()));
        return bookMapper.mapToDtoList(books);
    }

    @Override
    public List<BookDto> updateCopiesSold(List<CartItemDto> itemsSold) {
        List<BookEntity> updatedBooks = new ArrayList<>(itemsSold.size());
        for (CartItemDto bookSoldDto : itemsSold) {
            final BookEntity book = findBookById(bookSoldDto.getBookId());

            final Long updatedCopiesSoldNumber = book.getSoldCopiesNumber()+bookSoldDto.getQuantity();
            book.setSoldCopiesNumber(updatedCopiesSoldNumber);

            final Long updatedCopiesOnStock = book.getQuantityOnStock() - bookSoldDto.getQuantity();
            book.setQuantityOnStock(updatedCopiesOnStock);

            final BookEntity updatedBook = bookRepository.save(book);
            updatedBooks.add(updatedBook);
        }
        return bookMapper.mapToDtoList(updatedBooks);
    }


    private BookEntity findBookById(Long bookId){
        final Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        optionalBookEntity.orElseThrow(() -> new ObjectNotFoundException("Book", bookId));
        return optionalBookEntity.get();
    }

    private AuthorEntity findAuthorById(Long authorId){
        Optional<AuthorEntity> optionalAuthorEntity = authorRepository.findById(authorId);
        optionalAuthorEntity.orElseThrow(() -> new ObjectNotFoundException("Author", authorId));
        return optionalAuthorEntity.get();
    }

    private GenreEntity findGenreById(Long genreId){
        Optional<GenreEntity> optionalGenreEntity = genreRepository.findById(genreId);
        optionalGenreEntity.orElseThrow(() -> new ObjectNotFoundException("Genre", genreId));
        return optionalGenreEntity.get();
    }
}
