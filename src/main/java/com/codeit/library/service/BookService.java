package com.codeit.library.service;

import com.codeit.library.domain.Book;
import com.codeit.library.dto.request.BookCreateRequest;
import com.codeit.library.dto.request.BookUpdateRequest;
import com.codeit.library.dto.response.BookResponse;
import com.codeit.library.exception.BookNotFoundException;
import com.codeit.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookResponse createBook(BookCreateRequest request) {
        Book book = new Book(
            request.getTitle(),
            request.getAuthor(),
            request.getIsbn(),
            request.getPrice(),
            request.getPublishedDate()
        );
        
        Book saved = bookRepository.save(book);
        return BookResponse.from(saved);
    }

    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        return BookResponse.from(book);
    }

    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
            .map(BookResponse::from)
            .collect(Collectors.toList());
    }

    public List<BookResponse> findByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
            .map(BookResponse::from)
            .collect(Collectors.toList());
    }

    public List<BookResponse> searchByTitle(String keyword) {
        return bookRepository.findByTitleContaining(keyword).stream()
            .map(BookResponse::from)
            .collect(Collectors.toList());
    }

    public List<BookResponse> searchBooks(String author, Integer minPrice, Integer maxPrice) {
        return bookRepository.searchBooks(author, minPrice, maxPrice).stream()
            .map(BookResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        
        book.updateInfo(request.getTitle(), request.getPrice());
        
        return BookResponse.from(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public BookResponse applyDiscount(Long id, int discountRate) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        
        book.applyDiscount(discountRate);
        
        return BookResponse.from(book);
    }

    public List<BookResponse> findExpensiveRecentBooks(Integer minPrice, LocalDate fromDate) {
        return bookRepository.findExpensiveRecentBooks(minPrice, fromDate).stream()
            .map(BookResponse::from)
            .collect(Collectors.toList());
    }
}

