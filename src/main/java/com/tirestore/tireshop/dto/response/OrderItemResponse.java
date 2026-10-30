package com.tirestore.tireshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tirestore.tireshop.enums.ProductType;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemResponse(
    int itemId,
    BigDecimal priceAtPurchase,
    Integer quantity,
    String name,
    ProductType productType,
    String publicId
) {}
