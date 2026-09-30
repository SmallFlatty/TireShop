package com.tirestore.tireshop.entity;


import com.tirestore.tireshop.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class Item {

    public Item (String name, BigDecimal price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.isActive = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "item_id")
    private int itemId;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(Types.OTHER)
    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();// !-
}
