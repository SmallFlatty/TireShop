package com.tirestore.tireshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tirestore.tireshop.entity.OrderItem;
import com.tirestore.tireshop.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderResponse(
        int orderId,
        OrderStatus orderStatus,
        LocalDateTime createdAt,
        BigDecimal total,

        List<OrderItem> orderItems
) {}
