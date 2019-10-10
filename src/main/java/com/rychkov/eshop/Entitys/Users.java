package com.rychkov.eshop.Entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Users {
    public enum Role{
        CUSTOMER,
        EMPLOYEE,
        ADMIN
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Role role;
    private String firstName;
    private String secondName;
    private Date birthDate;
    private String email;
    private String password;
}
