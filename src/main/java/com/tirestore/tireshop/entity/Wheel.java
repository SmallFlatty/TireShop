package com.tirestore.tireshop.entity;


import com.tirestore.tireshop.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wheel")
@PrimaryKeyJoinColumn(name = "wheel_id")
public class Wheel extends Item{

    public Wheel(String name, BigDecimal price, Integer stock, Integer holesNum, BigDecimal circleDiameter, BigDecimal width, Integer diameter) {
        super(name, price, stock);
        super.setProductType(ProductType.WHEEL);
        this.holesNum = holesNum;
        this.circleDiameter = circleDiameter;
        this.width = width;
        this.diameter = diameter;
    }

    @Column(name = "diameter")
    private Integer diameter;

    @Column(name = "width")
    private BigDecimal width;

    @Column(name = "circle_diameter")
    private BigDecimal circleDiameter;

    @Column(name = "holes_num")
    private Integer holesNum;
}
