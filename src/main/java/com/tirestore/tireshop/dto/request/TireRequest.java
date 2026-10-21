package com.tirestore.tireshop.dto.request;

import com.tirestore.tireshop.enums.SeasonType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TireRequest(
        @NotNull @Size(max = 150) @NotBlank String name,
        @NotNull @Size(max = 244) String description,
        @Digits(integer = 8,fraction = 2) @NotNull @Positive BigDecimal price,
        @NotNull @PositiveOrZero Integer stock,
        @NotNull @Positive Integer width,
        @NotNull @Positive Integer profile,
        @NotNull @Pattern(regexp = "[LMNPQRSTUHVWYZ]") String speedRating,
        @NotNull @Positive Integer diameter,
        @NotNull SeasonType seasonType
) {}
