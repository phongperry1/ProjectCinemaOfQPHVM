package com.example.Quan.registration;

import lombok.Data;

import java.util.List;

import com.example.Quan.mo.Role;

/**
 * @author Sampson Alfred
 */
@Data
public class RegistrationRequest {
    private String userName;
    private String email;
    private String password;
    private List<Role> roles;
}
