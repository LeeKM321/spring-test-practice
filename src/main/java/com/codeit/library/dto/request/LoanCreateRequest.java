package com.codeit.library.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanCreateRequest {

    @NotNull(message = "회원 ID는 필수입니다")
    private Long memberId;

    @NotNull(message = "책 ID는 필수입니다")
    private Long bookId;
}

