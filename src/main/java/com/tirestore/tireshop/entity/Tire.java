package com.tirestore.tireshop.entity;


import com.tirestore.tireshop.enums.SeasonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tire")
public class Tire extends Item {
    @Column(name = "width")
    private int width;

    @Column(name = "profile")
    private int profile;

    @Column(name = "speed_rating")
    private char speedRating;

    @Column(name = "diameter")
    private int diameter;

    @Column(name = "season_type")
    private SeasonType seasonType;
}
