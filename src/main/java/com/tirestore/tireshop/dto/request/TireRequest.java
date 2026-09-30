package com.tirestore.tireshop.dto.request;

import com.tirestore.tireshop.enums.SeasonType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TireRequest(
        @Size(max = 150) @NotBlank String name,
        String description,
        @Digits(integer = 8,fraction = 2) @NotNull @Positive BigDecimal price,
        @NotNull @PositiveOrZero Integer stock,
        @Positive Integer width,
        @Positive Integer profile,
        Character speedRating,
        @Positive Integer diameter,
        SeasonType seasonType
) {}
