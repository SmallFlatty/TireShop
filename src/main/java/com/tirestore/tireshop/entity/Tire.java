package com.tirestore.tireshop.entity;

import com.tirestore.tireshop.enums.ProductType;
import com.tirestore.tireshop.enums.SeasonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tire")
@PrimaryKeyJoinColumn(name = "tire_id")
public class Tire extends Item {

    public Tire(String name, BigDecimal price, Integer stock, Integer width, Integer profile, Character speedRating, Integer diameter, SeasonType seasonType) {
        super(name, price, stock);
        super.setProductType(ProductType.TIRE);
        this.width = width;
        this.profile = profile;
        this.speedRating = speedRating;
        this.diameter = diameter;
        this.seasonType = seasonType;
    }

    @Column(name = "width")
    private Integer width;

    @Column(name = "profile")
    private Integer profile;

    @Column(name = "speed_rating")
    private Character speedRating;

    @Column(name = "diameter")
    private Integer diameter;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(Types.OTHER)
    @Column(name = "season_type")
    private SeasonType seasonType;
}
