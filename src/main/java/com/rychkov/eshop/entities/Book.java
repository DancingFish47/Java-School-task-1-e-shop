package com.rychkov.eshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Book")
public class Book extends AbstractEntity {
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "author")
    private String author;

    @NotNull
    @Column(name = "price")
    private Float price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private BookCategory bookCategory;

    @NotNull
    @Column(name = "pages")
    private Integer pages;

    @NotNull
    @Column(name = "amount")
    private Integer amount;

    @NotNull
    @Column(name = "sold")
    private Integer sold;

}
