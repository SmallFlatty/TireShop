package com.tirestore.tireshop.entity;


import com.tirestore.tireshop.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class Item {

    public Item (String name, ProductType productType, double price, int stock) {
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.stock = stock;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "item_id")
    private long itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();// !-
}
