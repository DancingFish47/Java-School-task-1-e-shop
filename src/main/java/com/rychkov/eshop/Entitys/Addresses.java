package com.rychkov.eshop.Entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Addresses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userId;
    private String country;
    private String city;
    private String zip;
    private String street;
    private Integer building;
    private String apartment;
}
