package com.tirestore.tireshop.dto.request;

import com.tirestore.tireshop.enums.SeasonType;

import java.math.BigDecimal;

public record TireFilterRequest(
        Integer width,
        Integer profile,
        Character speedRating,
        Integer diameter,
        SeasonType seasonType,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {}
