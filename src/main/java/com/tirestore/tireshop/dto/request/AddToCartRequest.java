package com.tirestore.tireshop.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddToCartRequest(
      @NotNull Integer itemId,
      @NotNull @Positive Integer quantity
) {}
