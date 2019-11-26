package com.rychkov.eshop.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "payment_method")
public class PaymentMethod extends AbstractEntity {
    @NotNull
    @Column(name = "name")
    private String name;

}
