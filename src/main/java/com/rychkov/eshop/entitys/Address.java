package com.rychkov.eshop.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "Address")
public class Address extends AbstractEntity {
    @ManyToOne(targetEntity = User.class)
    @NotNull
    private User user;

    @NotNull
    @Column(name = "country")
    private String country;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "zip")
    private String zip;

    @NotNull
    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "building")
    private String building;

    @Column(name="apartment")
    private String apartment;
}
