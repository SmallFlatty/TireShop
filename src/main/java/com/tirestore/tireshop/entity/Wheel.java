package com.tirestore.tireshop.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wheel")
public class Wheel extends Item{


    @Column(name = "diameter")
    private int diameter;

    @Column(name = "width")
    private int width;

    @Column(name = "circle_diameter")
    private float circle_diameter;

    @Column(name = "holes_num")
    private int holesNum;
}
