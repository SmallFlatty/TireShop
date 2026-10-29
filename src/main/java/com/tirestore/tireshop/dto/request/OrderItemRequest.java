package com.tirestore.tireshop.dto.request;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequest(
        @Positive @NotBlank @Id Integer itemId,
        @Digits(integer = 8,fraction = 2) @NotNull @Positive BigDecimal price,
        @Positive @NotNull int quantity
){}
