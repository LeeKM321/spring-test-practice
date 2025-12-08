package com.codeit.library.exception;

public class LoanLimitExceededException extends RuntimeException {
    
    public LoanLimitExceededException() {
        super("최대 3권까지 대출 가능합니다");
    }
    
    public LoanLimitExceededException(String message) {
        super(message);
    }
}

