package com.codeit.library.controller;

import com.codeit.library.dto.request.LoanCreateRequest;
import com.codeit.library.dto.response.LoanResponse;
import com.codeit.library.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody LoanCreateRequest request) {
        LoanResponse response = loanService.createLoan(request);
        return ResponseEntity
            .created(URI.create("/api/loans/" + response.getId()))
            .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable Long id) {
        LoanResponse response = loanService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getLoans() {
        List<LoanResponse> response = loanService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current")
    public ResponseEntity<List<LoanResponse>> getCurrentLoans() {
        List<LoanResponse> response = loanService.findCurrentLoans();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<LoanResponse>> getLoansByMember(@PathVariable Long memberId) {
        List<LoanResponse> response = loanService.findByMemberId(memberId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnBook(@PathVariable Long id) {
        LoanResponse response = loanService.returnBook(id);
        return ResponseEntity.ok(response);
    }
}

