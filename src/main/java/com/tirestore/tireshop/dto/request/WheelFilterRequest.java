package com.tirestore.tireshop.dto.request;

import java.math.BigDecimal;

public record WheelFilterRequest(
        Integer holesNum,
        BigDecimal circleDiameter,
        BigDecimal width,
        Integer diameter,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {}
