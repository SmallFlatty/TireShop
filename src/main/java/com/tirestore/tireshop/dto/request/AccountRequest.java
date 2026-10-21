package com.tirestore.tireshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequest(
        @NotNull @NotBlank String fullName,
        @NotNull @NotBlank String password,
        @NotNull @NotBlank String email
) {}
