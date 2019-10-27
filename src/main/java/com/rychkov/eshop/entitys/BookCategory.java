package com.rychkov.eshop.entitys;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Book_Category")

public class BookCategory extends AbstractEntity {
    @NotNull
    private String name;

}
