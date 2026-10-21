package com.tirestore.tireshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WheelResponse(
        int itemId,
        String name,
        String description,
        BigDecimal price,
        String publicId,

        Integer diameter,
        BigDecimal width,
        BigDecimal circleDiameter,
        Integer holesNum,
        Integer stock
) {}
