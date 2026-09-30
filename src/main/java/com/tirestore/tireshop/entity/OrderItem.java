package com.tirestore.tireshop.entity;


import com.tirestore.tireshop.entity.id.OrderItemId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem {

    @EmbeddedId
    private OrderItemId orderItemId = new OrderItemId();

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private Order order;// !-

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id")
    private Item item;// !-

    @Column(name = "price_at_purchase")
    private BigDecimal pricePurchase;

    @Column(name = "quantity")
    private int quantity;

}
