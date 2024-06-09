package com.example.Quan.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Quan.mo.User;

import java.util.Collection;
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
    private int status; // Add status field

    private List<GrantedAuthority> authorities;

    public EndToEndUserDetails(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.isEnabled = user.isEnabled();
        this.status = user.getStatus(); // Set status
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
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
        return status == 1; // Assuming 1 means active status
    }

}
