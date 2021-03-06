package com.rychkov.eshop.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Data
@Transactional
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "user")
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
    @Column(name = "role")
    private String userRole;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.userRole));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
