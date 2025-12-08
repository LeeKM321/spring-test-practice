package com.codeit.library.service;

import com.codeit.library.domain.Book;
import com.codeit.library.domain.Loan;
import com.codeit.library.domain.Member;
import com.codeit.library.dto.request.LoanCreateRequest;
import com.codeit.library.dto.response.LoanResponse;
import com.codeit.library.exception.*;
import com.codeit.library.repository.BookRepository;
import com.codeit.library.repository.LoanRepository;
import com.codeit.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoanService {

    private static final int MAX_LOAN_COUNT = 3;

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Transactional
    public LoanResponse createLoan(LoanCreateRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(() -> new MemberNotFoundException(request.getMemberId()));
        
        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(() -> new BookNotFoundException(request.getBookId()));

        // 비즈니스 규칙 검증
        validateLoanCreation(request.getMemberId(), request.getBookId());

        Loan loan = new Loan(member, book, LocalDate.now());
        Loan saved = loanRepository.save(loan);

        return LoanResponse.from(saved);
    }

    private void validateLoanCreation(Long memberId, Long bookId) {
        // 1. 대출 한도 확인 (최대 3권)
        long currentLoanCount = loanRepository.countByMemberIdAndReturnDateIsNull(memberId);
        if (currentLoanCount >= MAX_LOAN_COUNT) {
            throw new LoanLimitExceededException();
        }

        // 2. 책이 이미 대출중인지 확인
        if (loanRepository.existsByBookIdAndReturnDateIsNull(bookId)) {
            throw new BookAlreadyLoanedException();
        }

        // 3. 연체중인 대출이 있는지 확인
        if (loanRepository.existsOverdueLoan(memberId)) {
            throw new OverdueLoanExistsException();
        }
    }

    public LoanResponse findById(Long id) {
        Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new LoanNotFoundException(id));
        return LoanResponse.from(loan);
    }

    public List<LoanResponse> findAll() {
        return loanRepository.findAll().stream()
            .map(LoanResponse::from)
            .collect(Collectors.toList());
    }

    public List<LoanResponse> findByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException(memberId));
        
        return loanRepository.findByMember(member).stream()
            .map(LoanResponse::from)
            .collect(Collectors.toList());
    }

    public List<LoanResponse> findCurrentLoans() {
        return loanRepository.findByReturnDateIsNull().stream()
            .map(LoanResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public LoanResponse returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new LoanNotFoundException(loanId));
        
        loan.returnBook();
        
        return LoanResponse.from(loan);
    }
}

