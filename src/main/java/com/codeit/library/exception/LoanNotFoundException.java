package com.codeit.library.exception;

public class LoanNotFoundException extends RuntimeException {
    
    public LoanNotFoundException(String message) {
        super(message);
    }
    
    public LoanNotFoundException(Long id) {
        super("ID " + id + "인 대출 기록을 찾을 수 없습니다");
    }
}

