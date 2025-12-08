package com.codeit.library.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {

    private String title;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    @Max(value = 1000000, message = "가격은 1,000,000 이하여야 합니다")
    private Integer price;
}

