package com.tirestore.tireshop.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record WheelRequest(
        @Size(max = 150) @NotBlank String name,
        String description,
        @Digits(integer = 8,fraction = 2) @NotNull @Positive BigDecimal price,
        @NotNull @PositiveOrZero Integer stock,
        @Positive Integer diameter,
        @Digits(integer = 2,fraction = 1) @Positive BigDecimal width,
        @Digits(integer = 4, fraction = 1)@Positive BigDecimal circleDiameter,
        @Positive Integer holesNum
) {
}
