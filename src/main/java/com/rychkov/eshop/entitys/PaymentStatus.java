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
@Table(name = "Payment_Status")
public class PaymentStatus extends AbstractEntity {
    @NotNull
    @Column(name = "status")
    private String status;
}