package com.rychkov.eshop.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Order_Status")
public class OrderStatus extends AbstractEntity {
    @NotNull
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "orderStatus")
    private List<Order> orders;
}
