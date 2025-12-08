package com.codeit.library.exception;

public class BookAlreadyLoanedException extends RuntimeException {
    
    public BookAlreadyLoanedException() {
        super("이미 대출중인 책입니다");
    }
    
    public BookAlreadyLoanedException(String message) {
        super(message);
    }
}

