package com.tirestore.tireshop.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PopularItemResponse(
        int itemId,
        String name,
        String publicId,
        BigDecimal price
){}
