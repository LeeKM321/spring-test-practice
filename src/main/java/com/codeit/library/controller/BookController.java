package com.codeit.library.controller;

import com.codeit.library.dto.request.BookCreateRequest;
import com.codeit.library.dto.request.BookUpdateRequest;
import com.codeit.library.dto.response.BookResponse;
import com.codeit.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookCreateRequest request) {
        BookResponse response = bookService.createBook(request);
        return ResponseEntity
            .created(URI.create("/api/books/" + response.getId()))
            .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
        BookResponse response = bookService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks() {
        List<BookResponse> response = bookService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(
        @RequestParam(required = false) String author,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice
    ) {
        List<BookResponse> response = bookService.searchBooks(author, minPrice, maxPrice);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable String author) {
        List<BookResponse> response = bookService.findByAuthor(author);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
        @PathVariable Long id,
        @Valid @RequestBody BookUpdateRequest request
    ) {
        BookResponse response = bookService.updateBook(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/discount")
    public ResponseEntity<BookResponse> applyDiscount(
        @PathVariable Long id,
        @RequestParam int discountRate
    ) {
        BookResponse response = bookService.applyDiscount(id, discountRate);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

