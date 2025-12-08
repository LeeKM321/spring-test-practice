package com.codeit.library.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private LocalDate loanDate;

    private LocalDate returnDate;

    public Loan(Member member, Book book, LocalDate loanDate) {
        if (member == null) {
            throw new IllegalArgumentException("회원 정보는 필수입니다");
        }
        if (book == null) {
            throw new IllegalArgumentException("책 정보는 필수입니다");
        }
        if (loanDate == null) {
            throw new IllegalArgumentException("대출일은 필수입니다");
        }
        
        this.member = member;
        this.book = book;
        this.loanDate = loanDate;
    }

    public void returnBook() {
        if (this.returnDate != null) {
            throw new IllegalStateException("이미 반납된 책입니다");
        }
        this.returnDate = LocalDate.now();
    }

    public boolean isOverdue() {
        if (returnDate != null) {
            return false;
        }
        LocalDate dueDate = loanDate.plusDays(14);
        return LocalDate.now().isAfter(dueDate);
    }

    public boolean isReturned() {
        return returnDate != null;
    }
}

