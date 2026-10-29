package com.tirestore.tireshop.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record WheelRequest(
        @Size(max = 150) @NotBlank String name,
        @NotNull String description,
        @Digits(integer = 8,fraction = 2) @NotNull @Positive BigDecimal price,
        @NotNull @PositiveOrZero Integer stock,
        @NotNull @Positive Integer diameter,
        @NotNull @Digits(integer = 2,fraction = 1) @Positive BigDecimal width,
        @NotNull @Digits(integer = 4, fraction = 1)@Positive BigDecimal circleDiameter,
        @NotNull @Positive Integer holesNum
) {
}
