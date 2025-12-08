package com.codeit.library.exception;

public class BookNotFoundException extends RuntimeException {
    
    public BookNotFoundException(String message) {
        super(message);
    }
    
    public BookNotFoundException(Long id) {
        super("ID " + id + "인 책을 찾을 수 없습니다");
    }
}

