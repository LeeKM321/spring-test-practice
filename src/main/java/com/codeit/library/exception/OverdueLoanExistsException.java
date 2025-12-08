package com.codeit.library.exception;

public class OverdueLoanExistsException extends RuntimeException {
    
    public OverdueLoanExistsException() {
        super("연체중인 대출이 있습니다");
    }
    
    public OverdueLoanExistsException(String message) {
        super(message);
    }
}

