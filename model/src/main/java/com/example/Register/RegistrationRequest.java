package com.example.Register;

import lombok.Data;

import java.util.List;

import com.example.mo.Role;

/**
 * @author Sampson Alfred
 */
@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Role> roles;
}