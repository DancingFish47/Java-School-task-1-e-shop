package com.rychkov.eshop.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "Order_Status")
public class OrderStatus extends AbstractEntity {
    @NotNull
    @Column(name = "name")
    private String name;
}
