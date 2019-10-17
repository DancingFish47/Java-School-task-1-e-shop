package com.rychkov.eshop.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Book_Category")
public class BookCategory extends AbstractEntity {
    @NotNull
    private String name;

    @OneToMany(mappedBy = "bookCategory")
    private List<Book> books;
}
