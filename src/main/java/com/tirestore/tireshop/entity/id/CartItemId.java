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
public class CartItemId implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "item_id")
    private int itemId;
}
