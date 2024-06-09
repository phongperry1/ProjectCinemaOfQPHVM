package com.example.Service;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.mo.Users;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Sampson Alfred
 */
@Data
public class EndToEndUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean isEnabled;
    private boolean status; // Add status field

    private List<GrantedAuthority> authorities;

    public EndToEndUserDetails(Users user) {
        this.userName = user.getEmail();
        this.password = user.getUserPassword();
        this.isEnabled = user.isEnabled();
        this.status = user.isStatus(); // Set status
        this.authorities = Collections.singletonList(
            new SimpleGrantedAuthority(user.getRole().getNameRole())
    );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return status == true; // Assuming 1 means active status
    }

}