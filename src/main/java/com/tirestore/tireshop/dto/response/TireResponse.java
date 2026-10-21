package com.tirestore.tireshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tirestore.tireshop.enums.SeasonType;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TireResponse(
        int itemId,
        String name,
        String description,
        BigDecimal price,
        String publicId,

        Integer width,
        Integer profile,
        Character speedRating,
        Integer diameter,
        SeasonType seasonType,
        Integer stock

) {}
