package com.rychkov.eshop.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "book_category")

public class BookCategory extends AbstractEntity {
    @NotNull
    private String name;

}
