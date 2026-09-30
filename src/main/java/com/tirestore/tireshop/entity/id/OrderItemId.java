package com.tirestore.tireshop.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderItemId implements Serializable {

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "item_id")
    private int itemId;
}
