package com.rychkov.eshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Delivery_Method")
public class DeliveryMethod extends AbstractEntity {
    @NotNull
    @Column(name = "name")
    private String name;
}
