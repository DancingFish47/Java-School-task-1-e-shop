package com.rychkov.eshop.Entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Products {
    public enum Category{
        CAR,
        MOTORCYCLE,
        QUADBIKE
    }
    public enum Color{
        BLACK,
        WHITE,
        RED,
        BLUE
    }
    public enum SpeedType{
        FAST,
        SLOW
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Float price;
    private Category category;
    private Color color;
    private SpeedType speed;
    private Float weight;
    private Float volume;
    private Integer amount;
}
