package com.codeit.library.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "저자는 필수입니다")
    private String author;

    @Pattern(regexp = "^(\\d{3}-\\d{10}|\\d{13})$", message = "ISBN 형식이 올바르지 않습니다")
    private String isbn;

    @NotNull(message = "가격은 필수입니다")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    @Max(value = 1000000, message = "가격은 1,000,000 이하여야 합니다")
    private Integer price;

    private LocalDate publishedDate;
}

