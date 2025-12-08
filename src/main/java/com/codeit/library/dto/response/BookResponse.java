package com.codeit.library.dto.response;

import com.codeit.library.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer price;
    private LocalDate publishedDate;

    public static BookResponse from(Book book) {
        return new BookResponse(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getPrice(),
            book.getPublishedDate()
        );
    }
}

