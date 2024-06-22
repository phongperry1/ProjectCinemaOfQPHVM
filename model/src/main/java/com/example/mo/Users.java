package com.example.mo;

import java.sql.Date;
<<<<<<< HEAD

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
<<<<<<< HEAD
=======
@Table(name = "Users")  // Ensures the table name is correctly mapped
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD

    private int userId;

    private String userName;
    private String email;
    private String phone;
    private String userPassword;
    private String role;
    private boolean status;
    private String verificationCode;
=======
    private int userId;  // Changed from `UserId` to `userId` for consistency with field naming conventions

    private String userName;
    private String phone;
    private String email;
    private String userPassword;
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
    private Date birthdate;
    private String location;
    private String gender;
    private String userRank;
<<<<<<< HEAD
    private Integer memberPoints = 0; // Default value to avoid null
    private String paymentMethod;
    private String userType;
    private String profileImageURL;
    private String resetPasswordToken;

}
=======
    private int memberPoints;
    private String paymentMethod;
    private String userType;
    private String profileImageURL;
    private boolean status;  // Changed from `Status` to `status` for consistency with field naming conventions
}
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
