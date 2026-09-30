package com.tirestore.tireshop.entity;

import com.tirestore.tireshop.entity.id.CartItemId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cart")
public class CartItem {

    @EmbeddedId
    private CartItemId id = new CartItemId();

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Account account;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "quantity")
    private int quantity;
}
