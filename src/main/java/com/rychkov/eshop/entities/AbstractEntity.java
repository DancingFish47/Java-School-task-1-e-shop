package com.rychkov.eshop.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@MappedSuperclass
@Data
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "created_date")
    @CreationTimestamp
    private Date date;
}
