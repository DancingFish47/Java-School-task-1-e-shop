package com.rychkov.eshop.entitys;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Data
@Getter
@Setter
@Table(name = "User")
public class User extends AbstractEntity implements UserDetails {
    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "birthdate")
    private String birthdate;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @OneToOne(targetEntity = UserRole.class)
    private UserRole role;

    public User(String username, String password, String firstname, String lastname, String email, String birthdate, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.birthdate = birthdate;
        this.role.setId(1);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("User"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
