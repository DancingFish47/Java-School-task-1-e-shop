package com.rychkov.eshop.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "address")
public class Address extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
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

    @Column(name = "apartment")
    private String apartment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_status")
    private AddressStatus addressStatus;


}
