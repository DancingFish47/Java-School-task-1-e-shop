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
@Table(name = "Payment_Method")
public class PaymentMethod extends AbstractEntity {
    @NotNull
    @Column(name = "method")
    private String method;
}
