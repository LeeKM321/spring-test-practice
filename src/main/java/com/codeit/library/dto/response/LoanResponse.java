package com.codeit.library.dto.response;

import com.codeit.library.domain.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class LoanResponse {

    private Long id;
    private Long memberId;
    private String memberName;
    private Long bookId;
    private String bookTitle;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean overdue;

    public static LoanResponse from(Loan loan) {
        return new LoanResponse(
            loan.getId(),
            loan.getMember().getId(),
            loan.getMember().getName(),
            loan.getBook().getId(),
            loan.getBook().getTitle(),
            loan.getLoanDate(),
            loan.getReturnDate(),
            loan.isOverdue()
        );
    }
}

