package com.rychkov.eshop.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="Book")
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
    @OneToMany(targetEntity = BookCategory.class)
    @Column(name = "book_category_id")
    private Set<BookCategory> bookCategories = new HashSet<>();
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
