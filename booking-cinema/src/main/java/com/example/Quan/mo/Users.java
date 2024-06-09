package com.example.Quan.mo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;

/**
 * Author: Sampson Alfred
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    private String userName;
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private boolean isEnabled = false;
    private String phone;

    private String userPassword;

    private String birthdate;

    private String location;

    private String gender;

    private String captcha;

    private String userRank;

    private Integer memberPoints;

    private String idCard;

    private String paymentMethod;

    private String userType;

    private String profileImageURL;

    private boolean status = true; // Set default status to 1

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "UserId"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "UserId"))
    private Collection<Role> roles;

    public Users(String userName, String email,
            String password, Collection<Role> roles) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
