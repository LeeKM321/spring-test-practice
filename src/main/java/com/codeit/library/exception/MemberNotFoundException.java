package com.codeit.library.exception;

public class MemberNotFoundException extends RuntimeException {
    
    public MemberNotFoundException(String message) {
        super(message);
    }
    
    public MemberNotFoundException(Long id) {
        super("ID " + id + "인 회원을 찾을 수 없습니다");
    }
}

