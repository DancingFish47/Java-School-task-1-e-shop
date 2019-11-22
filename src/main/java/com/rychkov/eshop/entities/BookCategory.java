package com.rychkov.eshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Book_Category")

public class BookCategory extends AbstractEntity {
    @NotNull
    private String name;

}
