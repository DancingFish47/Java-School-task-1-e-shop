package com.rychkov.eshop.Entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private User user;
    private String country;
    private String city;
    private String zip;
    private String street;
    private Integer building;
    private String apartment;
}
